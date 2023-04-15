package com.titos.shareplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    /**
     * 评论ID
     */
    private Integer id;

    /**
     * 评论的用户ID
     */
    private Integer userId;

    /**
     * 评论的用户头像
     */
    private String headImage;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;
}
