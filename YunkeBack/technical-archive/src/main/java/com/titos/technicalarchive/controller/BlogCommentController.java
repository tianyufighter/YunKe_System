package com.titos.technicalarchive.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.model.Blog;
import com.titos.info.global.CommonResult;
import com.titos.technicalarchive.model.BlogComment;
import com.titos.technicalarchive.service.BlogCommentService;
import com.titos.technicalarchive.vo.BlogCommentVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客评论的控制层
 */
@RestController
@RequestMapping("/technicalArchive/comment")
public class BlogCommentController {
    @Resource
    private BlogCommentService blogCommentService;

    /**
     * 查询博客的评论
     * @param blogCommentVO
     * @return
     */
    @PostMapping("/getBlogComment")
    public CommonResult<PageInfo<BlogComment>> queryBlogComment(@RequestBody BlogCommentVO blogCommentVO) {
        PageInfo<BlogComment> pageInfo = blogCommentService.queryBlogCommentByCondition(blogCommentVO);
        return CommonResult.success(pageInfo);
    }

    /**
     * 新增一条博客的评论
     * @return
     */
    @PostMapping("/addBlogComment")
    public CommonResult addBlogComment(@RequestBody BlogComment blogComment) {
        Integer res = blogCommentService.addBlogComment(blogComment);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }
}
