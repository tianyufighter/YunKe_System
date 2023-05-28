package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.Likes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LikesDao {
    /**
     * 插入点赞信息
     * @param likes
     * @return
     */
    Integer insertLikes(Likes likes);

    /**
     * 根据帖子的id获取的用户id
     * @param postId 帖子id
     * @return
     */
    List<Integer> selectUserIdByPostId(Integer postId);

    /**
     * 动态查询点赞信息
     * @param likes
     * @return
     */
    List<Likes> selectLikesByCondition(Likes likes);

    /**
     * 根据点赞的id删除点赞信息
     * @param id 点赞的id
     * @return
     */
    Integer deleteLikesById(Integer id);

    /**
     * 根据用户id和帖子id删除点赞信息
     * @param userId 用户id
     * @param postId 帖子id
     * @return
     */
    Integer deleteLikesByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);

    /**
     * 根据帖子Id删除该帖子所对应的点赞信息
     * @param postId
     * @return
     */
    Integer deleteLikesByPostId(@Param("postId") Integer postId);

    /**
     * 查询用户一个时间段内的点赞量
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param userId 用户id
     * @return 点赞量情况
     */
    List<LocalDateTime> selectTimeByRecentPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Integer userId);
}
