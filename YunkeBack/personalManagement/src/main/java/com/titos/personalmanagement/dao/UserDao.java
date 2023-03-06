package com.titos.personalmanagement.dao;

import com.titos.info.user.entity.UserInfoDomain;
import com.titos.info.user.query.LoginQuery;
import com.titos.info.user.query.UserPassword;
import com.titos.personalmanagement.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作用户数据的Dao
 * @author Titos
 */
@Mapper
public interface UserDao {
    /**
     * 用户注册时，添加新用户
     * @param user 接收的参数封装为User对象
     * @return 添加的结果
     */
    Integer addNewUser(User user);

    /**
     * 根据username、email动态查询用户id(判断数据是否存在)
     * 使用动态sql来查询是否存在
     * @param user 用户对象
     * @return 用户id
     */
    Integer selectIdDynamic(User user);

    /**
     * 根据用户名或邮箱来查询用户
     * @param loginQuery 接收前端的参数封装的类
     * @return 返回查询的用户对象
     */
    User selectUserToLogin(LoginQuery loginQuery);

    /**
     * 用户用户id来动态更新用户信息
     * @param user 用户对象
     * @return 更新的结果
     */
    int updateUserInfoByIdSelective(User user);

    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     * @return 查询的用户对象
     */
    User selectUserInfoById(Integer id);

    /**
     * 根据用户id，用户名和邮箱同时查询该用户是否存在
     * @param id 用户id
     * @param username 用户名
     * @param email 邮箱
     * @return 用户信息
     */
    User selectUserForUpdate(Integer id, String username, String email);

    /**
     * 获取系统总人数
     * @return 人数
     */
    int getSystemUserCount();
}
