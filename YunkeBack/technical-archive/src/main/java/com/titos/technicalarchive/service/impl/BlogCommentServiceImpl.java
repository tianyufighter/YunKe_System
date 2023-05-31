package com.titos.technicalarchive.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.user.model.User;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.technicalarchive.dao.BlogCommentDao;
import com.titos.technicalarchive.model.BlogComment;
import com.titos.technicalarchive.service.BlogCommentService;
import com.titos.technicalarchive.vo.BlogCommentVO;
import com.titos.tool.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    @Resource
    private BlogCommentDao blogCommentDao;
    @Resource
    private UserServiceClient userServiceClient;

    @Override
    public Integer addBlogComment(BlogComment blogComment) {
        return blogCommentDao.insertBlogComment(blogComment);
    }

    @Override
    public PageInfo<BlogCommentVO> queryBlogCommentByCondition(BlogCommentVO blogCommentVO) {
        PageHelper.startPage(blogCommentVO.getPageNum(), blogCommentVO.getPageSize());
        List<BlogComment> blogCommentList = blogCommentDao.selectBlogComment(blogCommentVO);
        List<BlogCommentVO> blogCommentVOList = BeanCopyUtils.copyList(blogCommentList, BlogCommentVO.class);
        blogCommentVOList.forEach(temp -> {
            // 根据用户id获取用户信息
            User user = Convert.convert(User.class, userServiceClient.queryUserById(temp.getUserId()).getData());
            temp.setUser(user);
        });
        return new PageInfo<>(blogCommentVOList);
    }
}
