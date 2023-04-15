package com.titos.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 分页查询公告时接收前端的实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoticeVO {
    /**
     * 需要查询的页面
     */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
    /**
     * 通知的ID
     */
    private Integer id;
    /**
     * 通知的内容
     */
    private String noticeContent;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 公告的状态(1表示正常 0表示关闭)
     */
    private Boolean status;
    /**
     * 用户ID
     */
    private Integer userId;
}
