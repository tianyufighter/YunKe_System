package com.titos.info.technicalarchive.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @ClassName Blog
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/8 14:40
 **/
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
    @NotBlank(message = "文章标题不能为空")
    private String articleTitle;

    /**
     * 博客内容
     */
    @NotBlank(message = "文章内容不能为空")
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
    private LocalDateTime createTime;

}
