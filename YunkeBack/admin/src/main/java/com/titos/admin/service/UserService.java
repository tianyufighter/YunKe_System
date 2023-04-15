package com.titos.admin.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.personmanagement.vo.LoginSuccessVO;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;

import java.util.List;

public interface UserService {
    /**
     * 查询所有的用户消息
     * @return
     */
    PageInfo<User> queryAllUserByPage(Integer pageNum, Integer PageSize);

    /**
     * 添加用户
     * @param user 待添加的用户
     * @return
     */
    CommonResult addUser(User user);

    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User queryUserById(Integer id);

    /**
     * 根据用户id更新用户信息
     * @param user 接收的参数
     * @return 更新的结果
     */
    CommonResult updateUserByUserId(User user);

    /**
     * 根据用户id重置用户的密码
     * @param user 用户
     * @return
     */
    CommonResult resetUserPassword(User user);

    /**
     * 根据用户id删除用户信息
     * @param id 用户id
     * @return 删除的结果
     */
    CommonResult deleteUser(Integer id);

    /**
     * 根据条件动态查询用户
     * @return
     */
    PageInfo<User> queryUserByCondition(UserQuery userQuery);

    /**
     * 管理员登录系统时发出的登录请求
     */
    /**
     * 登录操作
     * @param loginVO
     * @return
     */
    CommonResult<LoginSuccessVO> login(LoginVO loginVO);
}
