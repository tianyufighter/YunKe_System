package com.titos.shareplatform.service.impl;

import cn.hutool.core.convert.Convert;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.shareplatform.dao.CommentDao;
import com.titos.shareplatform.dao.PostDao;
import com.titos.shareplatform.model.Comment;
import com.titos.info.post.model.Post;
import com.titos.shareplatform.service.CommentService;
import com.titos.shareplatform.vo.AddCommentVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Resource
    private PostDao postDao;

    @Resource
    private NormalServiceClient normalServiceClient;

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addComment(CustomStatement customStatement, AddCommentVO addCommentVO) {
        Comment comment = new Comment();
        comment.setPostId(addCommentVO.getPostId());
        // 过滤敏感词
        comment.setContent(Convert.convert(String.class, normalServiceClient.replaceContent(addCommentVO.getContent()).getData()));
        comment.setUserId(customStatement.getId());
        comment.setCreateTime(addCommentVO.getCreateTime());
        commentDao.insertComment(comment);
        // 根据帖子id获取帖子
        Post post = postDao.selectPostByPostId(comment.getPostId());
        // 更新评论量
        post.setComments(post.getComments() + 1);
        postDao.updatePostByIdDynamic(post);
    }

    @Transactional
    @Override
    public CommonResult<Boolean> deleteComments(CustomStatement customStatement, IdListVO idListVO) {
        // 查询当前用户所有评论的评论id
        List<Integer> allCommentIdList = commentDao.selectCommentIdByUserId(customStatement.getId());
        for (Integer commentId : idListVO.getIdList()) {
            if (!allCommentIdList.contains(commentId)) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
            Comment comment = commentDao.selectCommentById(commentId);
            postDao.updateComments(comment.getPostId(), -1);
            commentDao.deleteCommentBatchById(new Integer[]{commentId});
        }
        return CommonResult.success(Boolean.TRUE);
    }
}
