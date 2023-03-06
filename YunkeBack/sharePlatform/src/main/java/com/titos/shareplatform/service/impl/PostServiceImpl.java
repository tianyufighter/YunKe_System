package com.titos.shareplatform.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.redis.constant.RedisPrefixConst;
import com.titos.info.shareplatform.dto.CommentDTO;
import com.titos.info.shareplatform.entity.Likes;
import com.titos.info.shareplatform.entity.Post;
import com.titos.info.shareplatform.vo.*;
import com.titos.info.shareplatform.vo.TalentVO;
import com.titos.info.user.entity.User;
import com.titos.shareplatform.async.ServiceAsync;
import com.titos.shareplatform.dao.CommentDao;
import com.titos.shareplatform.dao.LikesDao;
import com.titos.shareplatform.dao.PostDao;
import com.titos.shareplatform.dao.UserDao;
import com.titos.shareplatform.service.PostService;
import com.titos.tool.BeanCopyUtils.BeanCopyUtils;
import com.titos.tool.sensitive.SensitiveFilter;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName PostServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/3/30 21:51
 **/
@Service
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {

    @Resource
    private PostDao postDao;

    @Resource
    private CommentDao commentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ServiceAsync serviceAsync;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private LikesDao likesDao;

    @Resource
    private SensitiveFilter sensitiveFilter;

    @Override
    public CommonResult<List<PostVO>> listPost(CustomStatement customStatement, Long pageNum, Long pageSize) {
        List<PostVO> postList = postDao.listPost((pageNum - 1) * pageSize, pageSize);
        for (PostVO post : postList) {
            // 包装点赞量
            Double likesCount = redisTemplate.opsForZSet().score(RedisPrefixConst.LIKE_COUNT, post.getId());
            post.setLikes(likesCount == null ? 0 : likesCount.intValue());
            // 包装点赞该帖子的用户头像
            Set<Object> set = redisTemplate.opsForSet().members(RedisPrefixConst.LIKE_KEY + post.getId());
            if (set != null) {
                List<User> likes = new ArrayList<>(set.size());
                set.forEach(item -> {
                    likes.add(userDao.selectOne(new LambdaQueryWrapper<User>()
                            .select(User::getHeadImage, User::getUsername)
                            .eq(User::getId, item)));
                });
                post.setLikesUser(likes);
            }

            // 封装当前帖子的点赞量
            post.setLikes(getCountFromRedis(RedisPrefixConst.LIKE_COUNT, post.getId()));

            // 包装当前用户是否点赞该帖子
            post.setIsLike(isLike(post.getId(), customStatement.getId()));

            // 包装该帖子的评论列表
            List<CommentDTO> commentList = commentDao.listUserByPostId(post.getId());
            post.setCommentList(commentList);
            post.setCommentBox("");
        }
        return CommonResult.success(postList);
    }

//    @Override
//    public CommonResult<List<MyPostVO>> listMyPost(CustomStatement customStatement, Long pageNum, Long pageSize) {
//        Page<Post> page = new Page<>(pageNum, pageSize);
//        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(Post::getId, Post::getTitle, Post::getContent, Post::getPostCover, Post::getLikes, Post::getCreateTime)
//                .orderByDesc(Post::getCreateTime)
//                .eq(Post::getUserId, customStatement.getId());
//        //Page<Post> postPage = postDao.selectPage(page, queryWrapper);
//        //List<MyPostVO> postList = BeanCopyUtils.copyList(postPage.getRecords(), MyPostVO.class);
//        return CommonResult.success(BeanCopyUtils.copyList(postDao.selectList(queryWrapper), MyPostVO.class));
//    }

    @Override
    public CommonResult<List<MyPostVO>> listMyPost(CustomStatement customStatement) {
        Page<Post> page = new Page<>();
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Post::getId, Post::getTitle, Post::getContent, Post::getPostCover, Post::getLikes, Post::getCreateTime)
                .orderByDesc(Post::getCreateTime)
                .eq(Post::getUserId, customStatement.getId());
        //Page<Post> postPage = postDao.selectPage(page, queryWrapper);
        //List<MyPostVO> postList = BeanCopyUtils.copyList(postPage.getRecords(), MyPostVO.class);
        return CommonResult.success(BeanCopyUtils.copyList(postDao.selectList(queryWrapper), MyPostVO.class));
    }

    @Override
    public CommonResult<List<TalentVO>> listTalent(Long pageNum, Long pageSize) {
        Set<ZSetOperations.TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().reverseRangeWithScores(
                RedisPrefixConst.TALENT, (pageNum - 1) * pageSize, pageNum * pageSize - 1);
        if (tupleSet == null) {
            return CommonResult.success(null);
        }
        List<TalentVO> talentList = new ArrayList<>(tupleSet.size());
        long rank = 1;
        for (ZSetOperations.TypedTuple<Object> sub : tupleSet) {
            User user = JSON.parseObject((String) sub.getValue(), User.class);
            if (user != null) {
                talentList.add(TalentVO.builder()
                        .rank(rank++)
                        .postCount(Objects.requireNonNull(sub.getScore()).intValue())
                        .userId(user.getId())
                        .username(user.getUsername())
                        .headImage(user.getHeadImage())
                        .build());
            }
        }
        return CommonResult.success(talentList);
    }

    @Async
    @Override
    public void addPost(CustomStatement customStatement, AddPostVO addPostVO) {
        // 过滤敏感词
        addPostVO.setContent(sensitiveFilter.filter(addPostVO.getContent()));
        addPostVO.setTitle(sensitiveFilter.filter(addPostVO.getTitle()));
        Post post = BeanCopyUtils.copyObject(addPostVO, Post.class);
        post.setUserId(customStatement.getId());
        post.setCreateTime(addPostVO.getCreateTime());
        postDao.insert(post);

        User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getHeadImage)
                .eq(User::getId, customStatement.getId()));
        log.info(JSON.toJSONString(user));
        redisTemplate.opsForZSet().incrementScore(RedisPrefixConst.TALENT, JSON.toJSONString(user), 1.0D);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deletePosts(CustomStatement customStatement, IdListVO idListVO) {
        for (Integer postId : idListVO.getIdList()) {
            if (!customStatement.getId().equals(postDao.selectById(postId).getUserId())) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
        }
        postDao.deleteBatchIds(idListVO.getIdList());

        // 删除帖子时，排行榜score-1
        serviceAsync.subTalentScore(customStatement);
        log.info("删除帖子完成");
        return CommonResult.success(Boolean.TRUE);
    }

    @Async
    @Override
    public void savePostLike(CustomStatement customStatement, LikesVO likesVO) {
        Integer userId = customStatement.getId();
        Integer postId = likesVO.getPostId();
        if (Boolean.TRUE.equals(isLike(postId, userId))) {
            // 若点赞了则取消点赞，并对应帖子的点赞量-1
            redisTemplate.opsForSet().remove(RedisPrefixConst.LIKE_KEY + postId, userId);
            redisTemplate.opsForZSet().incrementScore(RedisPrefixConst.LIKE_COUNT, postId, -1D);
            likesDao.delete(new LambdaQueryWrapper<Likes>()
                    .eq(Likes::getUserId, userId)
                    .eq(Likes::getPostId, postId));
        } else {
            // 若未点赞则点赞，并对应帖子的点赞量+1
            redisTemplate.opsForSet().add(RedisPrefixConst.LIKE_KEY + postId, userId);
            redisTemplate.opsForZSet().incrementScore(RedisPrefixConst.LIKE_COUNT, postId, 1D);
            likesDao.insert(Likes.builder()
                    .userId(userId)
                    .postId(postId)
                    .build());
        }
    }

    @Override
    public CommonResult<PostDataVO> getPostLike(CustomStatement customStatement) {
        LocalDateTime curDate = LocalDateTime.now();
        LocalDateTime preDate = curDate.plusDays(-31);
        log.info(curDate.toString());
        log.info(preDate.toString());
        List<LocalDateTime> postLikesDateList = likesDao.selectPostLikeByTime(preDate, curDate, customStatement.getId());
        int[] postLikeList = new int[31];
        for (LocalDateTime date : postLikesDateList) {
            int t = (int) date.until(curDate, ChronoUnit.DAYS);
            postLikeList[t]++;
            log.info("" + t);
        }

        List<LocalDateTime> postPublishedDateList = postDao.selectList(new LambdaQueryWrapper<Post>()
                .select(Post::getCreateTime)
                .ge(Post::getCreateTime, preDate)
                .le(Post::getCreateTime, curDate)
                .eq(Post::getUserId, customStatement.getId())).stream().map(Post::getCreateTime).collect(Collectors.toList());
        int[] postPublishedList = new int[31];
        for (LocalDateTime date : postPublishedDateList) {
            int t = (int) date.until(curDate, ChronoUnit.DAYS);
            postPublishedList[t]++;
        }
        return CommonResult.success(PostDataVO.builder()
                .postLikesTotal(postLikesDateList.size())
                .postLikes(Arrays.stream(postLikeList).boxed().collect(Collectors.toList()))
                .postPublishedTotal(postPublishedDateList.size())
                .postPublished(Arrays.stream(postPublishedList).boxed().collect(Collectors.toList()))
                .build());
    }

    /**
     * 从redis中取值
     *
     * @param key    键
     * @param postId 帖子ID
     * @return 值
     */
    private Integer getCountFromRedis(String key, Integer postId) {
        Double score = redisTemplate.opsForZSet().score(key, postId);
        score = score == null ? 0D : score;
        return score.intValue();
    }

    /**
     * 判断帖子是否被某个用户点赞
     *
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return 该用户是否点赞该帖子
     */
    private Boolean isLike(Integer postId, Integer userId) {
        return redisTemplate.opsForSet().isMember(RedisPrefixConst.LIKE_KEY + postId, userId);
    }

}
