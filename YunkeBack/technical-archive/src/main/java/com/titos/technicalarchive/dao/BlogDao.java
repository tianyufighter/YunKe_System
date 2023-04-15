package com.titos.technicalarchive.dao;

import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.post.model.Post;
import com.titos.technicalarchive.vo.BlogNumMonth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BlogDao {
    /**
     * 插入博客
     * @param blog
     * @return
     */
    Integer insertBlog(Blog blog);

    /**
     * 根据id删除博客
     * @param blogId
     * @return
     */
    Integer deleteBlogById(Integer blogId);

    /**
     * 按照pattern模糊查询用户的文章
     * @param id
     * @param pattern
     * @return
     */
    List<Blog> selectBlogByPattern(@Param("id") Integer id, @Param("pattern") String pattern);

    /**
     * 根据用户id查询当前用户文章
     * @param userId
     * @return
     */
    List<Blog> selectBlogByUserId(Integer userId);

    /**
     * 按分类查询文章
     * @param userId
     * @param category
     * @return
     */
    List<Blog> selectBlogByCategory(@Param("userId") Integer userId, @Param("category") String category);

    /**
     * 按分类和Pattern查询文章
     * @param userId
     * @param pattern
     * @param category
     * @return
     */
    List<Blog> selectBlogByPatternAndCategory(@Param("userId") Integer userId, @Param("pattern") String pattern, @Param("category") String category);

    /**
     * 根据博客id查询博客
     * @param id
     * @return
     */
    Blog selectBlogById(Integer id);

    /**
     * 根据用户id查询用户所有的目录信息
     * @param userId 用户id
     * @return
     */
    List<String> selectAllCategoryByUserId(Integer userId);

    /**
     * 根据用户id查询用户的文章数量
     * @param userId
     * @return
     */
    Integer selectBlogNumByUserId(Integer userId);

    /**
     * 查询用户今天发布的博客数量
     * @param userId
     * @return
     */
    Integer selectBlogNumToday(Integer userId);

    /**
     * 获取这个月当前用户的所有发布博客情况
     * @param userId
     * @return
     */
    List<BlogNumMonth> selectBlogNumNow(Integer userId);

    /**
     * 获取上个月当前用户的所有发布博客情况
     * @param userId
     * @return
     */
    List<BlogNumMonth> selectBlogNumLast(Integer userId);

    /**
     * 根据条件分页查询博客信息(管理员)
     * @param blogVO
     * @return
     */
    List<Blog> selectBlogByCondition(BlogVO blogVO);

    /**
     * 根据博客的id更新对应的博客信息
     * @param blog 博客信息
     * @return
     */
    Integer updateBlogById(Blog blog);
    /**
     * 根据博客id批量删除博客
     * @param ids
     * @return
     */
    Integer deleteBlogBatchById(List ids);
}
