package com.titos.technicalarchive.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;
import com.titos.technicalarchive.vo.BlogNumVO;
import com.titos.technicalarchive.vo.BlogStatusVO;

import java.util.List;

public interface BlogService {
    /**
     * 插入某用户的一篇博客
     * @param blog
     * @return
     */
    int insertBlog(Blog blog);

    /**
     * 删除谋篇博客
     * @param blogId
     * @return
     */
    int deleteBlog(Integer blogId);

    /**
     * 模糊查询当前用户的pattern,分类category的博客
     * 没有选择默认全查询
     * @param userId
     * @param pattern
     * @param category
     * @return
     */
    List<Blog> selectBlog(Integer userId, String pattern, String category);

    /**
     * 得到当前博客的内容信息
     * @param blogId
     * @return
     */
    Blog selectBlogDetail(Integer blogId);

    /**
     * 得到用户自定义的所有标签
     * @param userId
     * @return
     */
    List<String> selectAllCategory(Integer userId);

    /**
     * 获取当前用户的博客今日发布量和所有发布量,上个月和本月发布的数量情况
     * @param id
     * @return
     */
    BlogNumVO selectBlogNum(Integer id);

    /**
     * 按照条件分页查询博客信息(管理员)
     * @param blogVO
     * @return
     */
    PageInfo<BlogVO> queryBlogByCondition(BlogVO blogVO);

    /**
     * 根据博客id更新对应的博客信息
     * @param blog
     * @return
     */
    Integer updateBlog(Blog blog);

    /**
     * 更新博客的状态
     * @param blogStatusVO
     * @return
     */
    CommonResult<Boolean> updateBlogStatus(BlogStatusVO blogStatusVO);

    /**
     * 批量删除博客信息(管理员)
     * @param idListVO 需要删除的Id列表
     * @return
     */
    Integer deleteBlogBatch(IdListVO idListVO);
}
