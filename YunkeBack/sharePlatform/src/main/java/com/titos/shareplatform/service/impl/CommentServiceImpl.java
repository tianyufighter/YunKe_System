package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.shareplatform.entity.Comment;
import com.titos.info.shareplatform.entity.Post;
import com.titos.info.shareplatform.vo.AddCommentVO;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.shareplatform.dao.CommentDao;
import com.titos.shareplatform.dao.PostDao;
import com.titos.shareplatform.service.CommentService;
import com.titos.tool.sensitive.SensitiveFilter;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CommentServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/10 11:54
 **/
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Resource
    private PostDao postDao;

    @Resource
    private SensitiveFilter sensitiveFilter;


    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addComment(CustomStatement customStatement, AddCommentVO addCommentVO) {
        // 过滤敏感词
        addCommentVO.setCommentContent(sensitiveFilter.filter(addCommentVO.getCommentContent()));
        commentDao.insert(Comment.builder()
                .userId(customStatement.getId())
                .postId(addCommentVO.getPostId())
                .content(addCommentVO.getCommentContent())
                .createTime(addCommentVO.getCreateTime())
                .build());
        Post post = new Post();
        post.setId(addCommentVO.getPostId());
        postDao.update(post, Wrappers.update(post).setSql("`comments`=`comments`+1"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteComments(CustomStatement customStatement, IdListVO idListVO) {
        List<Integer> curCommentIdList = commentDao.selectList(new LambdaQueryWrapper<Comment>()
                .select(Comment::getId)
                .eq(Comment::getUserId, customStatement.getId())).stream().map(Comment::getId).collect(Collectors.toList());

        for (Integer commentId : idListVO.getIdList()) {
            if (!curCommentIdList.contains(commentId)) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
            postDao.subComments(commentId);
            commentDao.deleteById(commentId);
        }
        return CommonResult.success(Boolean.TRUE);
    }
}
