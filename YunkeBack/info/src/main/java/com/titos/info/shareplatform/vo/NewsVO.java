package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName NewsVO
 * @Description 新闻实体类
 * @Author Kurihada
 * @Date 2022/4/9 21:47
 **/
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

}
