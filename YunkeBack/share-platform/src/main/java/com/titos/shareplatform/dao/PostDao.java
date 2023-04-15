package com.titos.shareplatform.dao;

import com.titos.info.post.model.Post;
import com.titos.info.post.vo.PostNumVO;
import com.titos.shareplatform.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PostDao {
    /**
     * 插入一条帖子信息
     * @param post
     * @return
     */
    Integer insertPost(Post post);

    /**
     * 根据帖子的id动态修改帖子信息
     * @param post
     * @return
     */
    Integer updatePostByIdDynamic(Post post);

    /**
     * 根据帖子id批量删除帖子
     * @param ids
     * @return
     */
    Integer deletePostBatchById(List ids);

    /**
     * 根据用户的id查询用户所有的帖子信息
     * @param userId
     * @return
     */
    List<Post> selectAllPostByUserId(Integer userId);

    /**
     * 根据帖子的id查询帖子
     * @param postId
     * @return
     */
    Post selectPostByPostId(Integer postId);

    /**
     * 查询所有的帖子信息
     * @return
     */
    List<PostVO> selectAllPost();

    /**
     * 逆序查出所有的帖子信息
     * @return
     */
    List<PostVO> selectAllPostByDESC();

    /**
     * 查询制定一段时间内的用户发布帖子的时间
     * @return
     */
    List<LocalDateTime> selectTimeByRecentPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);

    /**
     * 根据帖子id来修改评论量
     * @param postId 帖子id
     * @param count 修改的变量
     */
    void updateComments(@Param("postId") Integer postId, @Param("count") Integer count);

    /**
     * 按条件查询帖子信息
     * @param postNumVO 帖子信息
     * @return
     */
    List<Post> selectPostByCondition(PostNumVO postNumVO);
}
