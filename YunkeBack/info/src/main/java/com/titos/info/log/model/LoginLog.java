package com.titos.info.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统登录记录表
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginLog {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
}
