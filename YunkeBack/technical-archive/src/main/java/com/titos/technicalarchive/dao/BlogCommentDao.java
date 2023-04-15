package com.titos.technicalarchive.dao;

import com.titos.technicalarchive.model.BlogComment;
import com.titos.technicalarchive.vo.BlogCommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogCommentDao {
    /**
     * 新增一条评论
     * @param blogComment
     * @return
     */
    Integer insertBlogComment(BlogComment blogComment);

    /**
     * 根据条件查询博客的评论信息
     * @param blogCommentVO
     * @return
     */
    List<BlogComment> selectBlogComment(BlogCommentVO blogCommentVO);

    /**
     * 根据博客id统计该博客的评论数量
     * @return
     */
    Integer selectCountByBlogId(Integer blogId);
}
