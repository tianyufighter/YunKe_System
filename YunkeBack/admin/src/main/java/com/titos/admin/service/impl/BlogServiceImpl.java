package com.titos.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import com.titos.admin.service.BlogService;
import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;
import com.titos.rpc.clients.BlogServiceClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogServiceClient blogServiceClient;
    @Override
    public PageInfo<BlogVO> queryBlogByCondition(BlogVO blogVO) {
        PageInfo<BlogVO> pageInfo = Convert.convert(PageInfo.class, blogServiceClient.getBlogByCondition(blogVO).getData());
        System.out.println("返回的博客数据: " + pageInfo);
        return pageInfo;
    }

    @Override
    public CommonResult updateBlog(Blog blog) {
        return blogServiceClient.updateBlog(blog);
    }

    @Override
    public CommonResult deleteBlog(IdListVO idListVO) {
        return blogServiceClient.deleteBlogBatch(idListVO);
    }
}
