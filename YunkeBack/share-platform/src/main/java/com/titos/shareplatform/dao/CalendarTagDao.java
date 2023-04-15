package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.CalendarTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarTagDao {
    /**
     * 根据calendar的id来查询tagId
     * @param calendarId
     * @return
     */
    Integer selectTagIdByCalendarId(Integer calendarId);

    /**
     * 根据calendarId查询id
     * @param calendarIds
     * @return
     */
    List<Integer> selectIdsByCalendarId(List<Integer> calendarIds);

    /**
     * 根据id批量删除CalendarTag
     * @param ids
     * @return
     */
    Integer deleteCalendarTagByIdBatch(List<Integer> ids);

    /**
     * 插入calendarTag
     * @param calendarTag
     * @return
     */
    Integer insertCalendarTag(CalendarTag calendarTag);
}
