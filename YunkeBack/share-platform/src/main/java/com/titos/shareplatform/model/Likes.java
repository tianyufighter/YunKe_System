package com.titos.shareplatform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户点赞帖子的关系实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Likes {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 点赞的用户id
     */
    private Integer userId;
    /**
     * 被点赞的帖子id
     */
    private Integer postId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
