package com.titos.technicalarchive.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.technicalarchive.dao.BlogCommentDao;
import com.titos.technicalarchive.model.BlogComment;
import com.titos.technicalarchive.service.BlogCommentService;
import com.titos.technicalarchive.vo.BlogCommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    @Resource
    private BlogCommentDao blogCommentDao;

    @Override
    public Integer addBlogComment(BlogComment blogComment) {
        return blogCommentDao.insertBlogComment(blogComment);
    }

    @Override
    public PageInfo<BlogComment> queryBlogCommentByCondition(BlogCommentVO blogCommentVO) {
        PageHelper.startPage(blogCommentVO.getPageNum(), blogCommentVO.getPageSize());
        List<BlogComment> blogCommentList = blogCommentDao.selectBlogComment(blogCommentVO);
        return new PageInfo<>(blogCommentList);
    }
}
