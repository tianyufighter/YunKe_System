package com.titos.info.news.vo;

import com.titos.info.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收前端数据封装的实体类
 * @ClassName News
 * @Description 新闻实体类
 * @Author Kurihada
 * @Date 2022/4/9 17:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsConditionVO {
    /**
     * 当前页
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
