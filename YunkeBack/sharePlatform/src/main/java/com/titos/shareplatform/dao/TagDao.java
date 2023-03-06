package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TagDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 23:17
 **/
@Repository
public interface TagDao extends BaseMapper<Tag> {
    /**
     * 根据任务ID查询分类名列表
     *
     * @param taskId 任务ID
     * @return 分类名列表
     */
    List<String> listTagNameByTaskId(Integer taskId);

    /**
     * 根据日历事件ID查询分类名列表
     * @param calendarId 日历事件ID
     * @return 分类名
     */
    String listTagNameByCalendarId(Integer calendarId);
}
