package com.titos.admin.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;

public interface BlogService {
    /**
     * 根据条件分页查询博客信息
     * @param blogVO
     * @return
     */
    PageInfo<Blog> queryBlogByCondition(BlogVO blogVO);

    /**
     * 根据博客id更新博客信息
     * @param blog 博客信息
     * @return
     */
    CommonResult updateBlog(Blog blog);

    /**
     * 批量删除博客
     * @param idListVO
     * @return
     */
    CommonResult deleteBlog(IdListVO idListVO);
}
