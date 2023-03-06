package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Comment;
import com.titos.info.shareplatform.vo.AddCommentVO;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.tool.token.CustomStatement;

/**
 * @ClassName CommentService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/10 11:53
 **/
public interface CommentService extends IService<Comment> {

    /**
     * 新增评论
     *
     * @param customStatement 用户消息
     * @param addCommentVO    评论内容
     */
    void addComment(CustomStatement customStatement, AddCommentVO addCommentVO);

    /**
     * 批量删除评论
     *
     * @param customStatement 用户消息
     * @param idListVO   需要删除的评论ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteComments(CustomStatement customStatement, IdListVO idListVO);
}
