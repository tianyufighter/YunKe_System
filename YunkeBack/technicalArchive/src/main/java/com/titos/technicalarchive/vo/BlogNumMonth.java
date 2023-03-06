package com.titos.technicalarchive.vo;

import lombok.Data;

/**
 * @author: ddgo
 * @dateTime: 2022/4/19 15:28
 * @version: 1.0.0
 * @description:
 */
@Data
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
