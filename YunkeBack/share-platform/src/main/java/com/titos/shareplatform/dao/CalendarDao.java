package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.Calendar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarDao {
    /**
     * 根据用户id查询Calendar事件
     * @param userId
     * @return
     */
    List<Calendar> selectCalendarsByUserId(Integer userId);

    /**
     * 插入信息
     * @param calendar
     * @return
     */
    int insertCalendar(Calendar calendar);

    /**
     * 根据id修改Calendar信息
     * @param calendar
     * @return
     */
    int updateCalendarById(Calendar calendar);

    /**
     * 根据用户id获取所有的calendarId
     * @param userId 用户id
     * @return
     */
    List<Integer> selectIdByUserId(Integer userId);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    Integer deleteCalendarByIdBatch(List<Integer> ids);
}
