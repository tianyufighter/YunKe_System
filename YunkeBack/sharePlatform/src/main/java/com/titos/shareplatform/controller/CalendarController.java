package com.titos.shareplatform.controller;

import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.vo.CalendarVO;
import com.titos.info.shareplatform.vo.FilterTimeVO;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.shareplatform.service.CalendarService;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CalendarController
 * @Description 日历事件Controller
 * @Author Kurihada
 * @Date 2022/4/13 22:41
 **/
@RestController
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

    @Resource
    private CalendarService calendarService;

    /**
     * 按照时间查询日历事件
     *
     * @param customStatement 用户信息
     * @return 日历时间列表
     */
    @InjectToken
    @GetMapping("/list")
    public CommonResult<List<CalendarVO>> listCalendar(
            CustomStatement customStatement) {
        return calendarService.listCalendar(customStatement);
    }

    /**
     * 新增或者更新日历事件
     *
     * @param customStatement 用户信息
     * @param calendarVO      日历事件
     * @return 是否成功
     */
    @InjectToken
    @PostMapping("/add")
    public CommonResult<Boolean> addOrUpdateCalendar(
            CustomStatement customStatement,
            @RequestBody CalendarVO calendarVO) {
        calendarService.addOrUpdateCalendar(customStatement, calendarVO);
        return CommonResult.success(Boolean.TRUE);
    }

    /**
     * 批量删除日历事件
     * @param customStatement 用户信息
     * @param idListVO 日历事件ID列表
     * @return 是否删除成功
     */
    @InjectToken
    @DeleteMapping("/delete")
    public CommonResult<Boolean> deleteCalendar(
            CustomStatement customStatement,
            @RequestBody IdListVO idListVO) {
        return calendarService.deleteCalendar(customStatement, idListVO);
    }
}
