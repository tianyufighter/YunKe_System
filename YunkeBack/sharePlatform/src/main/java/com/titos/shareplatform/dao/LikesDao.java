package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.entity.Likes;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName LikesDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/19 17:27
 **/
@Repository
public interface LikesDao extends BaseMapper<Likes> {
    /**
     * 查询用户一个时间段内点赞量情况
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param userId    用户ID
     * @return 点赞量情况
     */
    List<LocalDateTime> selectPostLikeByTime(LocalDateTime startDate, LocalDateTime endDate, Integer userId);
}
