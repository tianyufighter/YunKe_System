package com.titos.technicalarchive.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogNumMonth {
    /**
     * 发布日期
     */
    private Integer days;
    /**
     * 发布数量
     */
    private Integer total;
}
