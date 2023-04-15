package com.titos.info.user.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台管理模块根据条件查询用户时接收前端参数封装的类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserQuery {
    /**
     * 查询的页码
     */
    private Integer pageNum;
    /**
     * 查询的数量
     */
    private Integer pageSize;
    /**
     * 用户类型
     */
    private Integer personType;
    /**
     * 是否封禁
     */
    private Boolean isBan;
    /**
     * 性别
     */
    private String gender;
}
