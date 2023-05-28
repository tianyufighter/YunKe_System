package com.titos.personmanagement.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录时返回给前端的类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginQuery {
    private String token;
    private Integer id;
    private Integer role;
    private String username;
    private String headImage;
}
