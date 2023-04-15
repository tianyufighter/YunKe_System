package com.titos.technicalarchive.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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