package com.titos.tool.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * jwt中payload中的自定义变量的类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomStatement {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户类型
     */
    private Integer role;
    /**
     * 用户名
     */
    private String username;
}
