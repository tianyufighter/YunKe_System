package com.titos.technicalarchive.vo;

import lombok.Data;

/**
 * @author: ddgo
 * @dateTime: 2022/4/19 10:46
 * @version: 1.0.0
 * @description:
 */
@Data
public class BlogNumVO {
    /**
     * 今天的博客数量
     */
    private Integer todayNum;
    /**
     * 总的数量
     */
    private Integer allNum;

    /**
     * 本月发布的博客数量
     */
    private Integer []blogNumNow;

    /**
     * 上个月发布的帖子数量
     */
    private Integer []blogNumLast;
}
