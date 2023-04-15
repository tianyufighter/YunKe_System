package com.titos.userservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.userservice.dao.UserDao;
import com.titos.userservice.dto.UserDTO;
import com.titos.userservice.service.UserService;
import com.titos.info.user.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public Integer addUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public Integer queryUserIdDynamic(UserVO userVO) {
        return userDao.selectIdDynamic(userVO);
    }

    @Override
    public User queryUserByNameOrEmail(UserDTO userDTO) {
        return userDao.selectUserByNameOrEmail(userDTO);
    }

    @Override
    public Integer updateUserInfoSelective(User user) {
        return userDao.updateUserInfoByIdSelective(user);
    }

    @Override
    public User queryUserInfoById(Integer id) {
        return userDao.selectUserInfoById(id);
    }

    @Override
    public User queryUserByUserVo(UserVO userVO) {
        return userDao.selectUserForUpdate(userVO);
    }

    @Override
    public Integer querySystemUserCount() {
        return userDao.selectAllUserCount();
    }

    @Override
    public PageInfo<User> queryAllUserByPage(Integer pageNum, Integer pageSize) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        // 查询全部
        List<User> userList = userDao.selectAllUser();
        return new PageInfo<>(userList);
    }

    @Override
    public Integer deleteUserById(Integer id) {
        return userDao.deleteUserById(id);
    }

    @Override
    public PageInfo<User> queryUserByCondition(UserQuery userQuery) {
        // 分页查询
        PageHelper.startPage(userQuery.getPageNum(), userQuery.getPageSize());
        // 查询全部
        List<User> userList = userDao.selectUserByCondition(userQuery);
        return new PageInfo<>(userList);
    }

}
