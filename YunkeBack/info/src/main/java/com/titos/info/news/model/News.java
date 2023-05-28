package com.titos.info.news.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName News
 * @Description 新闻实体类
 * @Author Kurihada
 * @Date 2022/4/9 17:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {

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
     * 创建时间
     */
    private LocalDateTime createTime;

}
