package com.titos.info.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogVO {
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
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
    /**
     * 博客的评论量
     */
    private Integer commentCount;
    /**
     * 用户名
     */
    private String username;

    /**
     * 是否为最早发布
     */
    private Boolean isEarliest;

    /**
     * 请求参数(时间限制范围的信息)
     */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }
}
