package com.titos.shareplatform.service;

import com.titos.info.global.CommonResult;
import com.titos.shareplatform.vo.CalendarVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.tool.token.CustomStatement;

import java.util.List;

public interface CalendarService {
    /**
     * 按照时间查询日历事件
     *
     * @param customStatement 用户信息
     * @return 日历时间列表
     */
    CommonResult<List<CalendarVO>> listCalendar(CustomStatement customStatement);

    /**
     * 新增日历事件
     * @param customStatement 用户信息
     * @param calendarVO 日历事件
     */
    void addCalendar(CustomStatement customStatement, CalendarVO calendarVO);

    /**
     * 更新日历事件
     * @param customStatement 用户信息
     * @param calendarVO 日历事件
     */
    void updateCalendar(CustomStatement customStatement, CalendarVO calendarVO);

    /**
     * 批量删除日历事件
     * @param customStatement 用户信息
     * @param idListVO 日历事件ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteCalendar(CustomStatement customStatement, IdListVO idListVO);
}
