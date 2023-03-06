package com.titos.info.shareplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CalendarTag
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 23:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarTag {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日历ID
     */
    private Integer calendarId;

    /**
     * 标签ID
     */
    private Integer tagId;

}
