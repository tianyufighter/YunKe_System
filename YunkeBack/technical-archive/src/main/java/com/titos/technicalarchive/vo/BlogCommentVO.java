package com.titos.technicalarchive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.titos.info.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogCommentVO {
    /**
     * 查询的页码
     */
    private Integer pageNum;
    /**
     * 查询的数量
     */
    private Integer pageSize;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户信息
     */
    private User user;
    /**
     * 博客id
     */
    private Integer blogId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论发表的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
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
