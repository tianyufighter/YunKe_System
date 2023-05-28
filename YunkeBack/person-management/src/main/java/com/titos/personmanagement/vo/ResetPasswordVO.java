package com.titos.personmanagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 重置密码时接收前端的参数
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetPasswordVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * redis中对应的key
     */
    private String key;
    /**
     * 设置的新密码
     */
    private String password;
}
