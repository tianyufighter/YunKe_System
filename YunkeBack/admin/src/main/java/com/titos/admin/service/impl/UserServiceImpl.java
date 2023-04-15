package com.titos.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import com.titos.admin.config.YkSysConf;
import com.titos.admin.service.UserService;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.personmanagement.vo.LoginSuccessVO;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.query.UserQuery;
import com.titos.info.user.vo.UserVO;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.tool.check.VerifyPasswordUtil;
import com.titos.tool.check.VerifyStringUtil;
import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenContent;
import com.titos.tool.token.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger sys_user_logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserServiceClient userServiceClient;
    @Resource
    private NormalServiceClient normalServiceClient;
    @Resource
    private YkSysConf ykSysConf;
    @Override
    public PageInfo<User> queryAllUserByPage(Integer pageNum, Integer pageSize) {
        PageInfo pageInfo = Convert.convert(PageInfo.class, userServiceClient.queryAllUserByPage(pageNum, pageSize).getData());
        return pageInfo;
    }

    @Override
    public CommonResult addUser(User user) {
        user.setRegistryTime(new Date());
        user.setIsBan(false);
        // 验证数据是否重复
        Optional<CommonResult> res = verifyUser(user);
        if (res.isPresent()) {
            return res.get();
        }
        // 加密后存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);
        CommonResult addResult = userServiceClient.addUser(user);
        if (addResult.getCode() == 200) {
            return new CommonResult(StatusEnum.SUCCESS.getCode(), "用户信息添加成功");
        } else {
            return new CommonResult(StatusEnum.FAIL.getCode(), "用户信息添加失败");
        }
    }

    @Override
    public User queryUserById(Integer id) {
        User user = Convert.convert(User.class, userServiceClient.queryUserById(id).getData());
        return user;
    }

    @Override
    public CommonResult updateUserByUserId(User user) {
        CommonResult res = userServiceClient.updateUserInfo(user);
        return res;
    }

    @Override
    public CommonResult resetUserPassword(User user) {
        // 根据用户id获取用户信息
        User userData = Convert.convert(User.class, userServiceClient.queryUserById(user.getId()).getData());
        if (userData == null) {
            return CommonResult.fail("用户信息不存在");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密后存储
        String encodePwd = passwordEncoder.encode(user.getPassword());
        userData.setPassword(encodePwd);
        CommonResult result = userServiceClient.updateUserInfo(userData);
        // 修改成功
        if (result.getCode() == 200) {
            return CommonResult.success("密码重置成功");
        } else {
            return CommonResult.fail("密码重置失败");
        }
    }

    @Override
    public CommonResult deleteUser(Integer id) {
        CommonResult commonResult = userServiceClient.deleteUserById(id);
        return commonResult;
    }

    @Override
    public PageInfo<User> queryUserByCondition(UserQuery userQuery) {
        PageInfo pageInfo = Convert.convert(PageInfo.class, userServiceClient.queryUserByCondition(userQuery).getData());
        return pageInfo;
    }

    /**
     * 处理用户的登录请求
     * @param loginVO
     * @return
     */
    @Override
    public CommonResult<LoginSuccessVO> login(LoginVO loginVO) {
        // 根据前端传递的参数查找数据库中的数据
        User user = userServiceClient.queryUserByNameOrEmail(loginVO).getData();
        if (user != null) {
            if (user.getPersonType() == 1) { // 等于1则是普通用户，不允许登录
                return new CommonResult<>(StatusEnum.AUTHORITY_LACK.getCode(), "你无权登录系统，请联系管理员");
            } else if (Boolean.TRUE.equals(user.getIsBan())) {
                return new CommonResult<>(StatusEnum.AUTHORITY_LACK.getCode(),  "你处于封禁状态，无法登录，请联系管理员");
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // 如果存在该用户
                if (passwordEncoder.matches(loginVO.getPassword(), user.getPassword())) {
                    // JWT的payload中的自定义的私有字段
                    CustomStatement customStatement = new CustomStatement();
                    customStatement.setId(user.getId());
                    customStatement.setUsername(user.getUsername());
                    customStatement.setRole(user.getPersonType());
                    TokenContent tokenContent = new TokenContent(customStatement, ykSysConf.getTokenSecretKey());
                    String token = TokenUtil.buildToken(tokenContent);
                    LoginSuccessVO loginSuccessVO = new LoginSuccessVO(token, user.getId(), user.getPersonType(), user.getUsername(), user.getHeadImage());
                    return new CommonResult<>(StatusEnum.SUCCESS.getCode(), loginSuccessVO, "用户登录成功");
                } else {
                    return new CommonResult<>(StatusEnum.PASSWORD_WRONG.getCode(), "邮箱或密码错误");
                }
            }
        }
        return new CommonResult<>(StatusEnum.USER_UNEXISTED.getCode(), "邮箱或密码错误");
    }

    /**
     * 验证用户的密码是否符合要求，数据库中是否有相同的用户名和邮箱
     * @param user 用户对象
     * @return 如果返回的对象不为空，则表明不符合指定要求，反之则满足条件
     */
    private Optional<CommonResult> verifyUser(User user) {
        //        // 密码不符合要求
        if (!VerifyPasswordUtil.isPwdStrong(user.getPassword())) {
            return Optional.of(new CommonResult(StatusEnum.PASSWORD_IS_NOT_STRONG.getCode(), "密码强度不足"));
        } else {
            // 验证用户名和邮箱是否已经存在
            return verifyRepeat(user.getUsername(), user.getEmail());
        }
    }

    /**
     * 验证用户名和邮箱在数据库中是否已经存在
     * @param username 用户名
     * @param email 邮箱
     * @return 验证结果，如果不存在返回空对象，存在返回CommonResult
     */
    private Optional<CommonResult> verifyRepeat(String username, String email) {
        CommonResult res = null;
        // 验证数据是否重复
        if (!VerifyStringUtil.isStringNull(username) && isUsernameExisted(username)) {
            res = new CommonResult(StatusEnum.USERNAME_EXISTED.getCode(), "用户名已经存在");
        }
        if (!VerifyStringUtil.isStringNull(email) && isEmailExisted(email)) {
            res = new CommonResult(StatusEnum.EMAIL_EXISTED.getCode(), "邮箱已经存在");
        }
        return Optional.ofNullable(res);
    }

    /**
     * 根据username查询数据库中是否已经存在数据
     * @param username 用户名
     * @return 查询结果
     */
    private boolean isUsernameExisted(String username) {
        UserVO userVO = new UserVO();
        userVO.setUsername(username);
        Integer userId = (Integer) userServiceClient.queryUserIdDynamic(userVO).getData();
        return userId != null;
    }
    /**
     * 根据email查询数据库中是否已经存在数据
     * @param email 邮箱
     * @return 查询结果
     */
    private boolean isEmailExisted(String email) {
        UserVO userVO = new UserVO();
        userVO.setEmail(email);
        Integer userId = (Integer) userServiceClient.queryUserIdDynamic(userVO).getData();
        return userId != null;
    }
}
