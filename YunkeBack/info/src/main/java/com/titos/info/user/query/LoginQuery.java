package com.titos.info.user.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登陆时，接收前端参数封装的实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginQuery {
    private String verifyCode;
    private String username;
    private String email;
    private String password;
    public LoginQuery(String email, String password, String verifyCode) {
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
    }
}
