package com.titos.technicalarchive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/12 10:06
 * @Version: 1.0.0
 * @Description:
 */
@Data
public class SimpleBlogVO {
    /**
     * 博客id
     */
    Integer id;
    /**
     * 文章标题
     */
    String articleTitle;
    /**
     * 文章内容
     */
    String articleContent;
    /**
     * 文章纯文本
     */
    String articleText;
    /**
     * 分类
     */
    String category;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime createTime;
}
