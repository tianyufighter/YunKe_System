package com.titos.info.personmanagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功返回给前端的类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginSuccessVO {
    private String token;
    private Integer id;
    private Integer role;
    private String username;
    private String headImage;
}
