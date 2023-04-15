package com.titos.personmanagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户修改密码接收前端封装的类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPasswordVO {
    /**
     * 用户id
     */
    private Integer userID;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户旧密码
     */
    private String oldPassword;
    /**
     * 用户新密码
     */
    private String newPassword;
}
