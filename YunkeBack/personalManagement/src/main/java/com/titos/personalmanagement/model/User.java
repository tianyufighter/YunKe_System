package com.titos.personalmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户的实体类信息
 * @author Titos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户id，唯一标识
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private String gender;
    /**
     * 邮箱账号
     */
    private String email;
    /**
     * 人员类型，系统中有三种人员类型。
     * ① 值为1时表示普通用户
     * ② 值为2时表示审核和发布人员
     * ③ 值为3时表示系统管理员
     */
    private Integer personType;
    /**
     * 头像地址
     */
    private String headImage;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 电话号码
     */
    private String  phone;
    /**
     * 注册时间
     */
    private Date registryTime;
    /**
     * 职位
     */
    private String jobTitle;
    /**
     * 描述信息
     */
    private String description;
}
