package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Calendar;
import com.titos.info.shareplatform.vo.CalendarVO;
import com.titos.info.shareplatform.vo.FilterTimeVO;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.tool.token.CustomStatement;

import java.util.List;

/**
 * @ClassName CalendarService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 22:42
 **/
public interface CalendarService extends IService<Calendar> {
    /**
     * 按照时间查询日历事件
     *
     * @param customStatement 用户信息
     * @return 日历时间列表
     */
    CommonResult<List<CalendarVO>> listCalendar(CustomStatement customStatement);

    /**
     * 新增或者更新日历事件
     * @param customStatement 用户信息
     * @param calendarVO 日历事件
     */
    void addOrUpdateCalendar(CustomStatement customStatement, CalendarVO calendarVO);

    /**
     * 批量删除日历事件
     * @param customStatement 用户信息
     * @param idListVO 日历事件ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteCalendar(CustomStatement customStatement, IdListVO idListVO);
}
