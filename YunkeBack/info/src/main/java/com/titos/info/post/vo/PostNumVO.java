package com.titos.info.post.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收前端包含分页信息的postVO类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostNumVO {
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
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
