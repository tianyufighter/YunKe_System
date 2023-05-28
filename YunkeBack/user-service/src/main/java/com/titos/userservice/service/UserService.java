package com.titos.userservice.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.info.user.vo.UserVO;
import com.titos.userservice.dto.UserDTO;

import java.util.List;

public interface UserService {
    /**
     * 添加一个用户
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 根据username、email动态查询用户id
     * @param userVO
     * @return
     */
    Integer queryUserIdDynamic(UserVO userVO);

    /**
     * 根据用户名或邮箱来查询用户
     * @param userDTO
     * @return
     */
    User queryUserByNameOrEmail(UserDTO userDTO);

    /**
     * 根据用户id来选择性的更新用户信息
     * @param user
     * @return
     */
    Integer updateUserInfoSelective(User user);

    /**
     * 根据用户id来查询用户信息
     * @param id
     * @return
     */
    User queryUserInfoById(Integer id);

    /**
     * 根据用户id，用户名和邮箱一起查询该用户
     * @param userVO
     * @return
     */
    User queryUserByUserVo(UserVO userVO);

    /**
     * 查询系统总人数
     * @return
     */
    Integer querySystemUserCount();

    /**
     * 分页查询用户信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> queryAllUserByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据用户id删除用户信息
     * @param id
     * @return
     */
    Integer deleteUserById(Integer id);
    PageInfo<User> queryUserByCondition(UserQuery userQuery);
}
