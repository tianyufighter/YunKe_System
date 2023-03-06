package com.titos.technicalarchive.Service;

import com.titos.technicalarchive.vo.BlogNumVO;
import com.titos.technicalarchive.vo.BlogVO;
import com.titos.technicalarchive.vo.DetailBlogVO;
import com.titos.technicalarchive.vo.SimpleBlogVO;

import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/11 20:45
 * @Version: 1.0.0
 * @Description:
 */
public interface BlogService {

    /**
     * 插入某用户的一篇博客
     * @param userId
     * @param blogVO
     * @return
     */
    int insertBlog(Integer userId, BlogVO blogVO);

    /**
     * 删除谋篇博客
     * @param blogId
     * @return
     */
    int deleteBlog(Integer blogId);

    /**
     * 模糊查询当前用户的pattern,分类category的博客
     * 没有选择默认全查询
     * @param id
     * @param pattern
     * @param category
     * @return
     */
    List<SimpleBlogVO> selectBlog(Integer id, String pattern, String category);

    /**
     * 得到当前博客的内容信息
     * @param blogId
     * @return
     */
    DetailBlogVO selectBlogDetail(Integer blogId);

    /**
     * 得到用户自定义的所有标签
     * @param id
     * @return
     */
    List<String> selectAllCategory(Integer id);

    /**
     * 获取当前用户的博客今日发布量和所有发布量,上个月和本月发布的数量情况
     * @param id
     * @return
     */
    BlogNumVO selectBlogNum(Integer id);

}
