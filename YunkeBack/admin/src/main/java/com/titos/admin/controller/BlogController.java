package com.titos.admin.controller;

import com.github.pagehelper.PageInfo;
import com.titos.admin.service.BlogService;
import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/blog")
public class BlogController {
    @Resource
    private BlogService blogService;

    /**
     * 根据条件分页查询博客信息
     * @param blogVO
     * @return
     */
    @PostMapping("/getBlogByCondition")
    public CommonResult<PageInfo<BlogVO>> queryBlogByCondition(@RequestBody BlogVO blogVO) {
        PageInfo<BlogVO> pageInfo = blogService.queryBlogByCondition(blogVO);
        return CommonResult.success(pageInfo);
    }
    @PostMapping("/updateBlog")
    public CommonResult updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }
    @PostMapping("/deleteBlog")
    public CommonResult deleteBlog(@RequestBody IdListVO idListVO) {
        return blogService.deleteBlog(idListVO);
    }
}
