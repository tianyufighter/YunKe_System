package com.titos.shareplatform.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarTag {
    /**
     * 主键ID
     */
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
