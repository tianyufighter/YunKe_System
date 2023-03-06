package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName TalentVO
 * @Description 活跃达人VO
 * @Author Kurihada
 * @Date 2022/4/3 21:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentVO implements Serializable {

    private static final long serialVersionUID = -8525063809930062980L;

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
