package com.titos.shareplatform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName CalendarVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 22:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarVO {

    /**
     * 日历事件ID
     */
    private Integer id;

    /**
     * 日历事件标题
     */
    private String calendarTitle;

    /**
     * 日历事件描述
     */
    private String calendarDesc;

    /**
     * 日历事件类别
     */
    private String tagName;

    /**
     * 日历事件开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 日历事件结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
