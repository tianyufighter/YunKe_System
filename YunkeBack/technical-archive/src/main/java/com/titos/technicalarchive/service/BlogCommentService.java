package com.titos.technicalarchive.service;

import com.github.pagehelper.PageInfo;
import com.titos.technicalarchive.model.BlogComment;
import com.titos.technicalarchive.vo.BlogCommentVO;

import java.util.List;

public interface BlogCommentService {
    /**
     * 插入博客评论
     * @param blogComment 博客评论
     * @return
     */
    Integer addBlogComment(BlogComment blogComment);

    /**
     * 根据条件分页查询博客的评论信息
     * @param blogCommentVO
     * @return
     */
    PageInfo<BlogCommentVO> queryBlogCommentByCondition(BlogCommentVO blogCommentVO);
}
