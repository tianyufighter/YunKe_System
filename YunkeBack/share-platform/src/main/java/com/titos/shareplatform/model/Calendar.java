package com.titos.shareplatform.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar {
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
     * 日历事件发布人ID
     */
    private Integer userId;

    /**
     * 日历事件开始时间
     */
    private LocalDateTime startTime;

    /**
     * 日历事件结束时间
     */
    private LocalDateTime endTime;

    /**
     * 日历事件创建时间
     */
    private LocalDateTime createTime;
}
