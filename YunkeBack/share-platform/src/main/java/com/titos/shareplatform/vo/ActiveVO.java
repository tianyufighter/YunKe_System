package com.titos.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活跃达人VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveVO{
    /**
     * 排名
     */
    private Long rank;

    /**
     * 发表帖子数量
     */
    private Integer postCount;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String headImage;
}
