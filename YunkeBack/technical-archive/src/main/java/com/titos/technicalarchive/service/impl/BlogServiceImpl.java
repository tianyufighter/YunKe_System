package com.titos.technicalarchive.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.user.model.User;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.technicalarchive.dao.BlogCommentDao;
import com.titos.technicalarchive.dao.BlogDao;
import com.titos.info.blog.model.Blog;
import com.titos.technicalarchive.service.BlogService;
import com.titos.technicalarchive.vo.BlogNumMonth;
import com.titos.technicalarchive.vo.BlogNumVO;
import com.titos.technicalarchive.vo.BlogStatusVO;
import com.titos.tool.utils.BeanCopyUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private BlogCommentDao blogCommentDao;
    @Resource
    private UserServiceClient userServiceClient;

    /**
     * 新增博客(但博客处于待审核状态，只能仅自己可见)
     * @param blog
     * @return
     */
    @Override
    public int insertBlog(Blog blog) {
        blog.setViolation(2);
        return blogDao.insertBlog(blog);
    }

    @Override
    public int deleteBlog(Integer blogId) {
        int cnt = blogDao.deleteBlogById(blogId);
        return cnt;
    }

    @Override
    public List<Blog> selectBlog(Integer userId, String pattern, String category) {
        List<Blog>  blogList = null;
        if (category == null && pattern == null) {
            blogList = blogDao.selectBlogByUserId(userId);
        } else if (category == null) {
            blogList = blogDao.selectBlogByPattern(userId, pattern);
        } else if (pattern == null) {
            blogList = blogDao.selectBlogByCategory(userId, category);
        } else {
            blogList = blogDao.selectBlogByPatternAndCategory(userId, pattern, category);
        }
        return blogList;
    }

    @Override
    public Blog selectBlogDetail(Integer blogId) {
        return blogDao.selectBlogById(blogId);
    }

    @Override
    public List<String> selectAllCategory(Integer userId) {
        List<String> categoryList = blogDao.selectAllCategoryByUserId(userId);
        return categoryList;
    }

    @Transactional
    @Override
    public BlogNumVO selectBlogNum(Integer userId) {
        BlogNumVO blogNumVO = new BlogNumVO();
        // 用户总的博客数量
        Integer totalAllNum = blogDao.selectBlogNumByUserId(userId);
        blogNumVO.setAllNum(totalAllNum);
        // 用户今天的博客数量
        Integer todayNum = blogDao.selectBlogNumToday(userId);
        blogNumVO.setTodayNum(todayNum);
        // 获取这个月的博客发布情况
        Integer[] blogNumNow = new Integer[31];
        Arrays.fill(blogNumNow, 0);
        List<BlogNumMonth> blogNumMonths = blogDao.selectBlogNumNow(userId);
        if (blogNumMonths != null) {
            for (BlogNumMonth blogNumMonth : blogNumMonths) {
                if (blogNumMonth.getDays() != null) {
                    blogNumNow[blogNumMonth.getDays() - 1] = blogNumMonth.getTotal();
                }
            }
        }
        // 获取上个月的博客发布情况
        Integer[] blogNumLast = new Integer[31];
        Arrays.fill(blogNumLast, 0);
        blogNumMonths = blogDao.selectBlogNumLast(userId);
        if (blogNumMonths != null) {
            for (BlogNumMonth blogNumMonth : blogNumMonths) {
                if (blogNumMonth.getDays() != null) {
                    blogNumLast[blogNumMonth.getDays() - 1] = blogNumMonth.getTotal();
                }
            }
        }
        blogNumVO.setBlogNumNow(blogNumNow);
        blogNumVO.setBlogNumLast(blogNumLast);
        return blogNumVO;
    }

    @Override
    public PageInfo<BlogVO> queryBlogByCondition(BlogVO blogVO) {
        PageHelper.startPage(blogVO.getPageNum(), blogVO.getPageSize());
        List<Blog> blogList = blogDao.selectBlogByCondition(blogVO);
        PageInfo blogPageInfo = new PageInfo(blogList);
        List<BlogVO> blogVOList = BeanCopyUtils.copyList(blogList, BlogVO.class);
        // 根据博客的id获取对应博客的评论量
        for (BlogVO blogVO1 : blogVOList) {
            Integer count = blogCommentDao.selectCountByBlogId(blogVO1.getId());
            // 根据用户id获取用户信息
            User user = Convert.convert(User.class, userServiceClient.queryUserById(blogVO1.getUserId()).getData());
            blogVO1.setUsername(user.getUsername());
            blogVO1.setCommentCount(count);
        }
        blogPageInfo.setList(blogVOList);
        return blogPageInfo;
    }

    @Override
    public Integer updateBlog(Blog blog) {
        return blogDao.updateBlogById(blog);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> updateBlogStatus(BlogStatusVO blogStatusVO) {
        blogStatusVO.getIdList().forEach(blogId -> {
            Blog blog = Blog.builder()
                    .id(blogId)
                    .status(blogStatusVO.getBlogStatus())
                    .build();
            blogDao.updateBlogById(blog);
        });
        return CommonResult.success(Boolean.TRUE);
    }

    @Override
    public Integer deleteBlogBatch(IdListVO idListVO) {
        return blogDao.deleteBlogBatchById(idListVO.getIdList());
    }
}
