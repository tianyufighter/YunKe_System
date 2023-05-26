package com.titos.technicalarchive.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.blog.model.Blog;
import com.titos.info.post.vo.IdListVO;
import com.titos.technicalarchive.service.BlogService;
import com.titos.technicalarchive.vo.BlogNumVO;
import com.titos.technicalarchive.vo.BlogStatusVO;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/technicalArchive/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    /**
     * 发布博客
     * @param customStatement
     * @param blog
     * @return
     */

    @InjectToken
    @PostMapping("/publishBlog")
    public CommonResult<Boolean> publishBlog(CustomStatement customStatement, @RequestBody Blog blog) {
        blog.setUserId(customStatement.getId());
        int cnt = blogService.insertBlog(blog);
        if(cnt != 1) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 删除指定用户的指定博客，只需要传博客id
     * @param blogId
     * @return
     */
    @PostMapping("/deleteBlog")
    public CommonResult<Boolean> deleteBlog(Integer blogId) {
        int cnt = blogService.deleteBlog(blogId);
        if(cnt != 1) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 模糊查询
     * @param pattern
     * @return
     */
    @InjectToken
    @PostMapping("/searchBlog")
    public CommonResult<List<Blog>> searchBlog(CustomStatement customStatement, String pattern, String category) {
        List<Blog> blogVOList = blogService.selectBlog(customStatement.getId(), pattern, category);
        if(blogVOList == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(blogVOList);
    }

    /***
     * 通过id查找博客
     * @param blogId
     * @return
     */
    @PostMapping("/getBlogDetail")
    public CommonResult<Blog> getBlogDetail(Integer blogId) {
        Blog detailBlogVO = blogService.selectBlogDetail(blogId);
        if(detailBlogVO == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(detailBlogVO);
    }

    @InjectToken
    @PostMapping("/getAllCategory")
    public CommonResult<List<String>> getAllCategory(CustomStatement customStatement) {
        List<String> stringList = blogService.selectAllCategory(customStatement.getId());
        if(stringList == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(stringList);
    }

    @InjectToken
    @GetMapping("/getBlogNum")
    public CommonResult<BlogNumVO> getBlogNum(CustomStatement customStatement) {
        BlogNumVO blogNumVO = blogService.selectBlogNum(customStatement.getId());
        if(blogNumVO == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(blogNumVO);
    }
    /**
     * 根据条件分页查询【个人】帖子信息
     * @param blogVO
     * @return
     */
    @InjectToken
    @PostMapping("/getMyBlogByCondition")
    public CommonResult<PageInfo<BlogVO>> getMyBlogByCondition(CustomStatement customStatement, @RequestBody BlogVO blogVO) {
        blogVO.setUserId(customStatement.getId());
        PageInfo<BlogVO> pageInfo = blogService.queryBlogByCondition(blogVO);
        return CommonResult.success(pageInfo);
    }

    /**
     * 根据条件分页查询帖子信息
     * @param blogVO
     * @return
     */
    @PostMapping("/getBlogByCondition")
    public CommonResult<PageInfo<BlogVO>> getBlogByCondition(@RequestBody BlogVO blogVO) {
        PageInfo<BlogVO> pageInfo = blogService.queryBlogByCondition(blogVO);
        return CommonResult.success(pageInfo);
    }

    /**
     * 根据博客id更新博客信息
     * @param blog 博客信息
     * @return
     */
    @PostMapping("/updateBlog")
    public CommonResult updateBlog(@RequestBody Blog blog) {
        Integer res = blogService.updateBlog(blog);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    /**
     * 更新博客公开状态
     * @param blogStatusVO 更新的信息
     * @return 是否更新成功
     */
    @PostMapping("/updateBlogStatus")
    public CommonResult<Boolean> updateBlogStatus(@RequestBody BlogStatusVO blogStatusVO) {
        return blogService.updateBlogStatus(blogStatusVO);
    }

    /**
     * 批量删除博客信息(管理员)
     * @param idListVO
     * @return
     */
    @PostMapping("/deleteBlogBatch")
    public CommonResult deleteBlogBatch(@RequestBody IdListVO idListVO) {
        Integer res = blogService.deleteBlogBatch(idListVO);
        if (res != 0) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }
}
