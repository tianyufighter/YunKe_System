package com.titos.info.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    /**
     * 博客Id
     */
    private Integer id;

    /**
     * 博客标题
     */
    private String articleTitle;

    /**
     * 博客内容
     */
    private String articleContent;

    /**
     * 博客封面
     */
    private String articleCover;

    /**
     * 博客作者ID
     */
    private Integer userId;

    /**
     * 博客分类名
     */
    private String category;

    /**
     * 文章状态
     * 状态值 1公开 2私密
     */
    private Integer status;

    /**
     * 博客创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createTime;
    /**
     * 博客的普通文本信息
     */
    private String articleText;
    /**
     * 是否违规
     * 0: 表示没有违反规则
     * 1: 表示违反规则
     * 2: 表示待审核
     */
    private Integer violation;
}
