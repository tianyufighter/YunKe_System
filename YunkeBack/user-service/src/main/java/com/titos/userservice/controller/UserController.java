package com.titos.userservice.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.userservice.convert.UserDTOConvert;
import com.titos.userservice.service.UserService;
import com.titos.info.user.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public CommonResult addUser(@RequestBody User user) {
        Integer res = userService.addUser(user);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    @PostMapping("/queryUserIdDynamic")
    public CommonResult queryUserIdDynamic(@RequestBody UserVO userVO) {
        Integer userId = userService.queryUserIdDynamic(userVO);
        return CommonResult.success(userId);
    }

    @PostMapping("/queryUserByNameOrEmail")
    public CommonResult<User> queryUserByNameOrEmail(@RequestBody LoginVO loginVO) {
        User user = userService.queryUserByNameOrEmail(UserDTOConvert.toUserDTOByLoginVO(loginVO));
        if (user != null) {
            return new CommonResult(StatusEnum.SUCCESS.getCode(), user);
        } else {
            return new CommonResult<>(StatusEnum.FAIL.getCode(), "查询失败");
        }
    }
    @PostMapping("/updateUserInfo")
    public CommonResult updateUserInfo(@RequestBody User user) {
        int res = userService.updateUserInfoSelective(user);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }
    @PostMapping("/queryUserById")
    public CommonResult queryUserById(@RequestBody Integer userId) {
        User user = userService.queryUserInfoById(userId);
        if (user != null) {
            return CommonResult.success(user);
        } else {
            return CommonResult.fail("查询失败");
        }
    }

    @PostMapping("/queryUserByALL")
    public CommonResult queryUserByUserVo(@RequestBody UserVO userVO) {
        User user = userService.queryUserByUserVo(userVO);
        if (user != null) {
            return CommonResult.success(user);
        } else {
            return CommonResult.fail("查询失败");
        }
    }
    @PostMapping("/queryAllPeopleCount")
    public CommonResult querySystemUserCount() {
        Integer res = userService.querySystemUserCount();
        return CommonResult.success(res);
    }

    /**
     * 分页查询所有的用户信息
     * @return
     */
    @GetMapping("/queryAllUserByPage")
    public CommonResult<PageInfo<User>> queryAllUserByPage(@RequestParam(name="pageNum", defaultValue = "1") Integer pageNum,
                                                       @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<User> allUsers = userService.queryAllUserByPage(pageNum, pageSize);
        if (allUsers != null) {
            return CommonResult.success(allUsers);
        } else {
            return CommonResult.fail();
        }
    }

    /**
     * 根据用户id删除用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("/deleteUserById")
    public CommonResult deleteUserById(@RequestParam("id") Integer id) {
        Integer res = userService.deleteUserById(id);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    /**
     * 根据条件选择性的查询用户信息
     * @param userQuery
     * @return
     */
    @PostMapping("/queryUserByCondition")
    public CommonResult<PageInfo>  queryUserByCondition(@RequestBody UserQuery userQuery) {
        PageInfo pageInfo =  userService.queryUserByCondition(userQuery);
        return CommonResult.success(pageInfo);
    }
}
