package com.titos.userservice.dao;


import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.info.user.vo.UserVO;
import com.titos.userservice.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    Integer insertUser(User user);

    /**
     * 根据username、email动态查询用户id(判断数据是否存在)
     * 使用动态sql来查询是否存在
     * @param userVO 用户对象
     * @return 用户id
     */
    Integer selectIdDynamic(UserVO userVO);

    /**
     * 根据用户名或邮箱来查询用户
     * @param userDTO 接收前端的参数封装的类
     * @return 返回查询的用户对象
     */
    User selectUserByNameOrEmail(UserDTO userDTO);

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
     * @param userVO
     * @return
     */
    User selectUserForUpdate(UserVO userVO);

    /**
     * 获取系统总人数
     * @return 人数
     */
    int selectAllUserCount();

    /**
     * 查询所有的用户
     * @return 所有的用户信息
     */
    List<User> selectAllUser();

    /**
     * 根据用户id删除用户信息
     * @param id 用户id
     * @return 删除的结果
     */
    Integer deleteUserById(Integer id);

    /**
     * 根据性别、用户类型、是否禁用这三个属性来动态查询用户
     * @param userQuery
     * @return
     */
    List<User> selectUserByCondition(UserQuery userQuery);
}
