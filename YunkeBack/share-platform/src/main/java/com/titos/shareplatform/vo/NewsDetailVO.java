package com.titos.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDetailVO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 新闻封面图
     */
    private String newsCover;

    /**
     * 新闻标题
     */
    private String newsTitle;

    /**
     * 新闻内容
     */
    private String newsContent;

    /**
     * 发布人ID
     */
    private Integer userId;

    /**
     * 发布人用户名
     */
    private String username;

    /**
     * 新闻标签名
     */
    private String tagName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}