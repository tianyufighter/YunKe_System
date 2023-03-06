package com.titos.info.shareplatform.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName Calendar
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 22:43
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar {

    /**
     * 日历事件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
