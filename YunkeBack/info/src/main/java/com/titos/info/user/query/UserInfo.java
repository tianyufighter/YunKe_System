package com.titos.info.user.query;

import java.util.Date;

/**
 * 修改用户信息是接收参数封装成的实体类
 * @author author
 */
public class UserInfo {
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
     * 电话号码
     */
    private Date phone;
}
