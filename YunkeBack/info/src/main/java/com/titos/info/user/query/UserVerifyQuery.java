package com.titos.info.user.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户验证时，接收前端的参数
 * @author Titos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyQuery {
    /**
     * 用户名
     */
    private String username;
    /**
     * redis中存储用户对象的key
     */
    private String key;
}
