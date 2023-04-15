package com.titos.info.log.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 分页查询登录日志时接收前端的实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginLogVO {
    /**
     * 需要查询的页面
     */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户id
     */
    private String username;
    /**
     * 浏览器类型
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登录状态 0成功 1失败
     */
    private String status;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 访问时间
     */
    private Date loginTime;
}
