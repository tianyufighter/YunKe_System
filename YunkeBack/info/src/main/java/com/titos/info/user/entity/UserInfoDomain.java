package com.titos.info.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 修改用户信息是接收参数封装成的实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoDomain {
    /**
     * 用户id，唯一标识
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 职位
     */
    private String jobTitle;
    /**
     * 描述信息
     */
    private String description;
}
