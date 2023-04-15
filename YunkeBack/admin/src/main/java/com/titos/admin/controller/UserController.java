package com.titos.admin.controller;

import com.github.pagehelper.PageInfo;
import com.titos.admin.service.UserService;
import com.titos.info.global.CommonResult;
import com.titos.info.personmanagement.vo.LoginSuccessVO;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 管理系统的登录请求
     * @param loginVO
     * @return
     */
    @PostMapping("/login")
    public CommonResult<LoginSuccessVO> login(@RequestBody LoginVO loginVO) {
        return userService.login(loginVO);
    }

    /**
     * 查询所有的用户信息
     * @return
     */
    @GetMapping("/allUserByPage")
    public CommonResult<PageInfo<User>> queryAllUserByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<User> pageInfo = userService.queryAllUserByPage(pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }
    /**
     * 添加新用户
     */
    @PostMapping("/addUser")
    public CommonResult addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/queryUserById")
    public CommonResult<User> queryUserById(Integer id) {
        User user = userService.queryUserById(id);
        return CommonResult.success(user);
    }

    /**
     * 根据用户id更新用户信息
     * @param user 待更新的用户信息
     * @return
     */
    @PostMapping("/updateUser")
    public CommonResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserByUserId(user);
    }

    /**
     * 根据用户id重置用户的密码
     * @param user
     * @return
     */
    @PostMapping("/resetPassword")
    public CommonResult resetUserPassword(@RequestBody User user) {
        return userService.resetUserPassword(user);
    }

    /**
     * 根据用户id删除用户信息
     * @param id
     * @return
     */
    @GetMapping("/deleteUser")
    public CommonResult deleteUser(Integer id) {
        return userService.deleteUser(id);
    }

    /**
     * 根据条件动态查询用户的信息
     * @param userQuery
     * @return
     */
    @PostMapping("/queryUserByCondition")
    public CommonResult<PageInfo> queryUserByCondition(@RequestBody UserQuery userQuery) {
        PageInfo pageInfo = userService.queryUserByCondition(userQuery);
        return CommonResult.success(pageInfo);
    }
}
