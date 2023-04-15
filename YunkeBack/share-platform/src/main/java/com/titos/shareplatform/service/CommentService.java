package com.titos.shareplatform.service;

import com.titos.info.global.CommonResult;
import com.titos.shareplatform.vo.AddCommentVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.tool.token.CustomStatement;

public interface CommentService {
    /**
     * 新增评论
     * @param customStatement 用户消息
     * @param comment 评论内容
     */
    void addComment(CustomStatement customStatement, AddCommentVO addCommentVO);

    /**
     * 批量删除评论
     * @param customStatement 用户消息
     * @param idListVO 需要删除的评论ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteComments(CustomStatement customStatement, IdListVO idListVO);

}
