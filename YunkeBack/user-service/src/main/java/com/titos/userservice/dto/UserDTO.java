package com.titos.userservice.dto;

import com.titos.info.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录时service层接收的信息的对象
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String verifyCode;
    private String username;
    private String email;
    private String password;
    public UserDTO(String email, String password, String verifyCode) {
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
    }
}