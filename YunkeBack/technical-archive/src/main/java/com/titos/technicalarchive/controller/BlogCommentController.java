package com.titos.technicalarchive.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.model.Blog;
import com.titos.info.global.CommonResult;
import com.titos.technicalarchive.model.BlogComment;
import com.titos.technicalarchive.service.BlogCommentService;
import com.titos.technicalarchive.vo.BlogCommentVO;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
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
    public CommonResult<PageInfo<BlogCommentVO>> queryBlogComment(@RequestBody BlogCommentVO blogCommentVO) {
        PageInfo<BlogCommentVO> pageInfo = blogCommentService.queryBlogCommentByCondition(blogCommentVO);
        return CommonResult.success(pageInfo);
    }

    /**
     * 新增一条博客的评论
     * @return
     */
    @InjectToken
    @PostMapping("/addBlogComment")
    public CommonResult addBlogComment(CustomStatement customStatement, @RequestBody BlogComment blogComment) {
        blogComment.setUserId(customStatement.getId());
        Integer res = blogCommentService.addBlogComment(blogComment);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }
}
