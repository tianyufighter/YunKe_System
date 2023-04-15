package com.titos.rpc.clients;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.info.user.vo.UserVO;
import com.titos.rpc.fallback.UserServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "userService", fallbackFactory = UserServiceClientFallbackFactory.class)
public interface UserServiceClient {
    @PostMapping("/user/addUser")
    CommonResult addUser(@RequestBody User user);

    @PostMapping("/user/queryUserIdDynamic")
    CommonResult queryUserIdDynamic(@RequestBody UserVO userVO);

    @PostMapping("/user/queryUserByNameOrEmail")
    CommonResult<User> queryUserByNameOrEmail(@RequestBody LoginVO loginVO);

    @PostMapping("/user/updateUserInfo")
    CommonResult updateUserInfo(@RequestBody User user);

    @PostMapping("/user/queryUserById")
    CommonResult queryUserById(@RequestBody Integer userId);

    @PostMapping("/user/queryUserByALL")
    CommonResult queryUserByUserVo(@RequestBody UserVO userVO);

    @PostMapping("/user/queryAllPeopleCount")
    CommonResult querySystemUserCount();

    @GetMapping("/user/queryAllUserByPage")
    CommonResult<PageInfo<User>> queryAllUserByPage(@RequestParam(name="pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize);

    @GetMapping("/user/deleteUserById")
    CommonResult deleteUserById(@RequestParam("id") Integer id);

    @PostMapping("/user/queryUserByCondition")
    CommonResult<PageInfo>  queryUserByCondition(@RequestBody UserQuery userQuery);
}
