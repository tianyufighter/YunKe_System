package com.titos.info.news.vo;

import com.titos.info.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 返回给前端封装的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsVO {

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
     * 新闻标签名
     */
    private String tagName;
    /**
     * 发布者名称
     */
    private String username;
    /**
     * 新闻的发布时间
     */
    private LocalDateTime createTime;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 发布新闻的用户
     */
    private User user;
}
