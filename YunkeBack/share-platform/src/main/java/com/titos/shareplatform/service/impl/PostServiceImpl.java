package com.titos.shareplatform.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;
import com.titos.info.user.model.User;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.shareplatform.async.ServiceAsync;
import com.titos.shareplatform.dao.CommentDao;
import com.titos.shareplatform.dao.LikesDao;
import com.titos.shareplatform.dao.PostDao;
import com.titos.shareplatform.dto.CommentDTO;
import com.titos.shareplatform.model.Likes;
import com.titos.info.post.model.Post;
import com.titos.shareplatform.pojo.RedisKeyConst;
import com.titos.shareplatform.service.PostService;
import com.titos.shareplatform.vo.*;
import com.titos.tool.utils.BeanCopyUtils;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    private PostDao postDao;

    @Resource
    private CommentDao commentDao;

    @Resource
    private LikesDao likesDao;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserServiceClient userServiceClient;

    @Resource
    private NormalServiceClient normalServiceClient;

    @Resource
    private ServiceAsync serviceAsync;

//    @Override
//    public PageInfo<Post> queryPostsByPage(int pageNum, int pageSize) {
//        // 分页查询(紧随跟在其后的第一条查询sql将会被分页)
//        PageHelper.startPage(pageNum, pageSize);
//        // 查询全部
//        List<Post> list = postDao.selectAllPost();
//        // 返回结果
//        PageInfo<Post> pageInfo = new PageInfo<>(list);
//        return pageInfo;
//    }

    @Override
    public List<PostVO> queryAllPosts() {
        return postDao.selectAllPost();
    }

    @Override
    public CommonResult<PageInfo<PostVO>> queryPostsByPage(CustomStatement customStatement, int pageNum, int pageSize) {
        // 分页查询(紧随跟在其后的第一条查询sql将会被分页)
        PageHelper.startPage(pageNum, pageSize);
        // 查询全部
        List<PostVO> postList = postDao.selectAllPostByDESC();
        for (PostVO postVO: postList) {
            // 根据用户id获取用户信息
            User poster = Convert.convert(User.class, userServiceClient.queryUserById(postVO.getUserId()).getData());
            postVO.setUser(poster);
            // 从redis中获取该帖子的点赞量
            Double likesCount = redisTemplate.opsForZSet().score(RedisKeyConst.LIKE_COUNT, postVO.getId());
            postVO.setLikes(likesCount == null ? 0 : likesCount.intValue());
            // 从redis中获取点赞该帖子的用户id
            Set<Object> userIds = redisTemplate.opsForSet().members(RedisKeyConst.LIKE_PREFIX + postVO.getId());
            if (userIds != null) {
                List<User> likesUser = new ArrayList<>(userIds.size());
                userIds.forEach(userId -> {
                    // 根据用户id获取用户信息
                    User user = Convert.convert(User.class, userServiceClient.queryUserById((Integer) userId).getData());
                    likesUser.add(user);
                });
                postVO.setLikesUser(likesUser);
            }
            // 从redis当中获取用户是否点赞该帖子
            Boolean isLike = redisTemplate.opsForSet().isMember(RedisKeyConst.LIKE_PREFIX + postVO.getId(), customStatement.getId());
            postVO.setIsLike(isLike);

            // 获取该帖子对应的所有评论信息
            List<CommentDTO> commentList = commentDao.selectCommentByPostId(postVO.getId());
            for (CommentDTO commentDTO : commentList) {
                User user = Convert.convert(User.class, userServiceClient.queryUserById(commentDTO.getUserId()).getData());
                commentDTO.setUsername(user.getUsername());
                commentDTO.setHeadImage(user.getHeadImage());
            }
            postVO.setCommentList(commentList);
            postVO.setCommentBox("");
        }
        return CommonResult.success(new PageInfo<>(postList));
    }

    @Override
    public CommonResult<List<Post>> listMyPost(CustomStatement customStatement) {
        List<Post> myPostVOList = postDao.selectAllPostByUserId(customStatement.getId());
        return CommonResult.success(myPostVOList);
    }

    @Override
    public CommonResult<List<ActiveVO>> listActive(Long pageNum, Long pageSize) {
        Set<ZSetOperations.TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().reverseRangeWithScores(RedisKeyConst.ACTIVE, (pageNum - 1) * pageSize, pageNum * pageSize - 1);
        if (tupleSet == null) {
            return CommonResult.success(null);
        }
        List<ActiveVO> activeVOList = new ArrayList<>(tupleSet.size());
        // 用户的排名
        long rank = 0;
        for (ZSetOperations.TypedTuple<Object> sub : tupleSet) {
            User user = JSON.parseObject((String) sub.getValue(), User.class);
            if (user != null) {
                activeVOList.add(ActiveVO.builder()
                        .rank(++rank)
                        .postCount(Objects.requireNonNull(sub.getScore()).intValue())
                        .userId(user.getId())
                        .username(user.getUsername())
                        .headImage(user.getHeadImage())
                        .build());
            }
        }
        return CommonResult.success(activeVOList);
    }

    @Async
    @Override
    public void addPost(CustomStatement customStatement, AddPostVO addPostVO) {
        // 过滤敏感词
        addPostVO.setContent(Convert.convert(String.class, normalServiceClient.replaceContent(addPostVO.getContent()).getData()));
        addPostVO.setTitle(Convert.convert(String.class, normalServiceClient.replaceContent(addPostVO.getTitle()).getData()));
        Post post = BeanCopyUtils.copyObject(addPostVO, Post.class);
        post.setUserId(customStatement.getId());
        post.setCreateTime(addPostVO.getCreateTime());
        post.setComments(0);
        post.setLikes(0);
        post.setIsViolation(false);
        postDao.insertPost(post);
        // 根据用户id获取用户信息
        User user = Convert.convert(User.class, userServiceClient.queryUserById(customStatement.getId()).getData());
        redisTemplate.opsForZSet().incrementScore(RedisKeyConst.ACTIVE, JSON.toJSONString(user), 1.0D);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deletePosts(CustomStatement customStatement, IdListVO postIdListVO) {
        for (Integer postId : postIdListVO.getIdList()) {
            // 判断需要删除的帖子中用户id与当前操作的用户id是否相等，如果不相等，则报错
            if (!customStatement.getId().equals(postDao.selectPostByPostId(postId).getUserId())) {
                return new CommonResult<>(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
        }
        postDao.deletePostBatchById(postIdListVO.getIdList());
        // 删除帖子时，存储的排行榜中的score(即该用户发表的帖子数量)减1
        serviceAsync.subActiveScore(customStatement);
        log.info("删除帖子完成");
        return CommonResult.success(Boolean.TRUE);
    }

    @Async
    @Override
    public void savePostLike(CustomStatement customStatement, LikesVO likesVO) {
        Integer userId = customStatement.getId();
        Integer postId = likesVO.getPostId();
        if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(RedisKeyConst.LIKE_PREFIX + postId, userId))) {
            // 若点赞了则取消点赞，并对帖子的点赞量-1
            redisTemplate.opsForSet().remove(RedisKeyConst.LIKE_PREFIX + postId, userId);
            redisTemplate.opsForZSet().incrementScore(RedisKeyConst.LIKE_COUNT, postId, -1D);
            likesDao.deleteLikesByUserIdAndPostId(userId, postId);
        } else {
            // 若未点赞则点赞，并对对应的帖子的点赞量+1
            redisTemplate.opsForSet().add(RedisKeyConst.LIKE_PREFIX + postId, userId);
            redisTemplate.opsForZSet().incrementScore(RedisKeyConst.LIKE_COUNT, postId, 1D);
            likesDao.insertLikes(Likes.builder()
                    .userId(userId)
                    .postId(postId)
                    .build());
        }
    }

    @Override
    public CommonResult<PostDataVO> getPostLike(CustomStatement customStatement) {
        LocalDateTime curDate =  LocalDateTime.now();
        LocalDateTime preDate = curDate.minusDays(31);
        log.info(curDate.toString());
        log.info(preDate.toString());
        List<LocalDateTime> postLikesDateList = likesDao.selectTimeByRecentPeriod(preDate, curDate, customStatement.getId());
        // 统计近一个月的点赞量
        int[] postLikeList = new int[31];
        for (LocalDateTime date : postLikesDateList) {
            int t = (int) date.until(curDate, ChronoUnit.DAYS);
            postLikeList[t]++;
            log.info(""+t);
        }
        // 统计近一个月帖子发布量
        List<LocalDateTime> postPublishedDateList = postDao.selectTimeByRecentPeriod(preDate, curDate, customStatement.getId());
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

    @Override
    public CommonResult<PageInfo<Post>> queryPostByCondition(PostNumVO postNumVO) {
        PageHelper.startPage(postNumVO.getPageNum(), postNumVO.getPageSize());
        List<Post> postList = postDao.selectPostByCondition(postNumVO);
        return CommonResult.success(new PageInfo<>(postList));
    }

    @Override
    public Integer updatePost(Post post) {
        return postDao.updatePostByIdDynamic(post);
    }

    @Override
    public Integer deletePostBatch(IdListVO idListVO) {
        Integer res = postDao.deletePostBatchById(idListVO.getIdList());
        // TODO: 将排行中对应的数据要减少
        log.info("删除帖子完成");
        return res;
    }
}
