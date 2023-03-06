package com.titos.technicalarchive.controller;

import com.alibaba.fastjson.JSONObject;
import com.titos.info.global.CommonResult;
import com.titos.technicalarchive.Service.BlogService;
import com.titos.technicalarchive.utils.CheckUtil;
import com.titos.technicalarchive.vo.BlogNumVO;
import com.titos.technicalarchive.vo.BlogVO;
import com.titos.technicalarchive.vo.DetailBlogVO;
import com.titos.technicalarchive.vo.SimpleBlogVO;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/11 20:45
 * @Version: 1.0.0
 * @Description:
 */
@RestController
@RequestMapping("/blog")
public class
BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 发布博客
     * @param customStatement
     * @param jsonObject
     * @return
     */

    @InjectToken
    @PostMapping("/publishBlog")
    public CommonResult<Boolean> publishBlog(CustomStatement customStatement, String jsonObject) {
        Integer userId = customStatement.getId();
        BlogVO blogVO = JSONObject.parseObject(jsonObject, BlogVO.class);
        int cnt = blogService.insertBlog(userId, blogVO);
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
    public CommonResult<List<SimpleBlogVO>> searchBlog(CustomStatement customStatement, String pattern, String category) {
        List<SimpleBlogVO> blogVOList = blogService.selectBlog(customStatement.getId(), pattern, category);
        if(blogVOList == CheckUtil.defaultErrorBlogVOList) {
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
    public CommonResult<DetailBlogVO> getBlogDetail(Integer blogId) {
        DetailBlogVO detailBlogVO = blogService.selectBlogDetail(blogId);
        if(detailBlogVO == CheckUtil.defaultErrorDetailBlogVO) {
            return CommonResult.fail();
        }
        return CommonResult.success(detailBlogVO);
    }

    @InjectToken
    @PostMapping("/getAllCategory")
    public CommonResult<List<String>> getAllCategory(CustomStatement customStatement) {
        List<String> stringList = blogService.selectAllCategory(customStatement.getId());
        if(stringList == CheckUtil.defaultErrorStringList) {
            return CommonResult.fail();
        }
        return CommonResult.success(stringList);
    }

    @InjectToken
    @GetMapping("/getBlogNum")
    public CommonResult<BlogNumVO> getBlogNum(CustomStatement customStatement) {
        BlogNumVO blogNumVO = blogService.selectBlogNum(customStatement.getId());
        if(blogNumVO == CheckUtil.defaultErrorBlogNumVO) {
            return CommonResult.fail();
        }
        return CommonResult.success(blogNumVO);
    }
}
