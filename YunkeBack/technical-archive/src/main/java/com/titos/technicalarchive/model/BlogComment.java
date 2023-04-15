package com.titos.technicalarchive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogComment {
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 博客id
     */
    private Integer blogId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论发表的时间
     */
    private LocalDateTime createTime;
}
