package com.titos.info.personmanagement.vo;

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
public class LoginVO {
    private String verifyCode;
    private String username;
    private String email;
    private String password;
    public LoginVO(String email, String password, String verifyCode) {
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
    }
}
