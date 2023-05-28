package com.titos.info.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 帖子所对应的实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {
    /**
     * 帖子的id
     */
    private Integer id;
    /**
     * 帖子标题
     */
    private String title;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 帖子的封面图片
     */
    private String postCover;
    /**
     * 发布帖子的用户id
     */
    private Integer userId;
    /**
     * 评论量
     */
    private Integer comments;
    /**
     * 点赞量
     */
    private Integer likes;
    /**
     * 帖子是否违规，默认是否
     */
    private Boolean isViolation;
    /**
     * 帖子发表时间
     */
    private LocalDateTime createTime;
}
