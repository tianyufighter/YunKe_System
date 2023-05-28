package com.titos.personmanagement.service.impl;

import cn.hutool.core.convert.Convert;
import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.info.exception.ParameterException;
import com.titos.info.file.FileInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.constant.CacheConstants;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.log.model.LoginLog;
import com.titos.info.personmanagement.vo.LoginSuccessVO;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.info.user.vo.UserVO;
import com.titos.personmanagement.config.YkSysConf;
import com.titos.personmanagement.convert.UserConvert;
import com.titos.personmanagement.factory.UserFactory;
import com.titos.personmanagement.mail.MailHandler;
import com.titos.personmanagement.service.PersonService;
import com.titos.personmanagement.vo.*;
import com.titos.rpc.clients.AdminServiceClient;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.tool.check.VerifyPasswordUtil;
import com.titos.tool.check.VerifyStringUtil;
import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenContent;
import com.titos.tool.token.TokenUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 操作用户数据的service层
 * @author Titos
 */
@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger sys_user_logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    @Resource
    private UserServiceClient userServiceClient;
    @Resource
    private YkSysConf ykSysConf;
    @Resource
    private NormalServiceClient normalServiceClient;
    @Resource
    private MailHandler mailHandler;

    @Resource
    private AdminServiceClient adminServiceClient;

    @Override
    public boolean isCaptchaEnabled() {
        return Convert.convert(Boolean.class, adminServiceClient.getCaptchaEnabled().getData());
    }

    @Override
    public CommonResult register(RegisterVO registerVO, String redisKey) {
        // 验证验证码是否正确
        CommonResult commonResult = checkVerifyCode(redisKey, registerVO.getVerifyCode());
        if (!StatusEnum.SUCCESS.getCode().equals(commonResult.getCode())) {
            return commonResult;
        }
        User user = UserConvert.toUserByRegisterVO(registerVO);
        // 验证用户密码是否符合要求，用户名或邮箱是否存在
        Optional<CommonResult> res = verifyUser(user);
        // 不满足，直接返回结果
        if (res.isPresent()) {
            return res.get();
        }
        // 如果开启了邮箱注册
        if (Convert.convert(Boolean.class, adminServiceClient.getEmailValidateEnabled().getData())) {
            // 将用户数据暂时存储到redis
            String key = (String) normalServiceClient.cacheInfo(new CacheInfoVO(user, 30L, TimeUnit.MINUTES, CacheConstants.REGISTER_CACHE_USER_KEY)).getData();

            boolean isSuccess = mailHandler.sendAccountVerify(user, key);
            if (isSuccess) {
                return new CommonResult(StatusEnum.SUCCESS.getCode(), "发送注册邮件成功");
            } else {
                return new CommonResult(StatusEnum.MAIL_ERROR.getCode(), "邮件发送失败");
            }
        } else {
            User newUser = doRegister(user);
            return new CommonResult(StatusEnum.SUCCESS.getCode(), newUser, "注册");
        }
    }

    /**
     * 进行注册，将注册信息
     * @param user
     * @return
     */
    private User doRegister(User user) {
        // 加密后存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);
        UserFactory userFactory = new UserFactory();
        User newUser = userFactory.build(user);
        newUser.setIsBan(false);
        userServiceClient.addUser(newUser);
        return newUser;
    }

    /**
     * 根据username查询数据库中是否已经存在数据
     * @param username 用户名
     * @return 查询结果
     */
    @Override
    public boolean isUsernameExisted(String username) {
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
    @Override
    public boolean isEmailExisted(String email) {
        UserVO userVO = new UserVO();
        userVO.setEmail(email);
        Integer userId = (Integer) userServiceClient.queryUserIdDynamic(userVO).getData();
        return userId != null;
    }

    @Override
    public CommonResult<LoginSuccessVO> login(LoginVO loginVO, String redisKey) {
        CommonResult commonResult = checkVerifyCode(redisKey, loginVO.getVerifyCode());
        if (!StatusEnum.SUCCESS.getCode().equals(commonResult.getCode())) {
            return commonResult;
        }
        // 根据前端传递的参数查找数据库中的数据
        User user = userServiceClient.queryUserByNameOrEmail(loginVO).getData();
        if (user != null) {
            if (Boolean.TRUE.equals(user.getIsBan())) {
                recordLoginLog(user.getUsername(), "Error", "账号处于封禁状态");
                return new CommonResult<>(StatusEnum.ACCOUNT_DISABLE.getCode(), "你处于封禁状态，无法登录，请联系管理员");
            } else if (validateLoginUsername(user.getUsername())) {
                recordLoginLog(user.getUsername(), "Error", "账号位于黑名单");
                return new CommonResult<>(StatusEnum.ACCOUNT_DISABLE.getCode(), "你处于系统黑名单中，无法登录，请联系管理员");
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
                    recordLoginLog(user.getUsername(), "Success", "用户登录成功");
                    return new CommonResult<>(StatusEnum.SUCCESS.getCode(), loginSuccessVO, "登录成功");
                } else {
                    recordLoginLog(user.getUsername(), "Error", "用户名或密码错误");
                    return new CommonResult<>(StatusEnum.PASSWORD_WRONG.getCode(), "用户名或密码错误");
                }
            }
        }
        recordLoginLog(user.getUsername(), "Error", "用户名或密码错误");
        return new CommonResult<>(StatusEnum.USER_UNEXISTED.getCode(), "用户名或密码错误");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public CommonResult verifyEmail(String username, String key) {
        User user = Convert.convert(User.class, normalServiceClient.getInfoByKey(key).getData());
        if (user == null) {
            return new CommonResult<>(StatusEnum.PARAM_ERROR.getCode(), "用户信息不存在");
        }
        // 验证数据是否重复
        Optional<CommonResult> res = verifyUser(user);
        // 数据重复
        if (res.isPresent()) {
            return res.get();
        }
        try {
            doRegister(user);
            return new CommonResult(StatusEnum.SUCCESS.getCode(), "注册成功");
        } catch (Exception e) {
            return new CommonResult(StatusEnum.VERIFY_ERROR.getCode(), "注册失败");
        }
    }
    /**
     * 修改用户信息
     * @param user 接收用户参数封装的实体类
     * @param customStatement 用户在token中的信息
     * @return 修改后的用户信息
     */
    @Override
    public CommonResult<User> modifyUserInfo(User user, CustomStatement customStatement) {
        userServiceClient.updateUserInfo(user);
        User userInfo = Convert.convert(User.class, userServiceClient.queryUserById(user.getId()).getData());
        return CommonResult.success(userInfo, "修改用户信息");
    }
    /**
     * 修改用户头像
     * @param image 用户头像文件
     * @param customStatement token中用户的信息部分
     * @return 保存成功后的用户图片在服务器上的url地址
     */
    @Override
    public CommonResult<String> modifyUserAvatar(MultipartFile image, CustomStatement customStatement) {
        if (image.isEmpty()) {
            return new CommonResult<>(StatusEnum.PARAM_ERROR.getCode(), "文件为空");
        }
        CommonResult<FileInfo> res = normalServiceClient.upload(image);
        if (!StatusEnum.SUCCESS.getCode().equals(res.getCode())) {
            return new CommonResult<>(StatusEnum.FILE_SIZE_ERROR.getCode(), "文件存储失败");
        }
        User user = new User();
        user.setId(customStatement.getId());
        user.setHeadImage(res.getData().getUrl());
        userServiceClient.updateUserInfo(user);
        return CommonResult.success(res.getData().getUrl(), "修改成功");
    }

    @Override
    public CommonResult modifyUserPassword(CustomStatement customStatement, UserPasswordVO userPasswordVO) {
        // 根据用户id获取用户的信息
        User user = Convert.convert(User.class, userServiceClient.queryUserByUserVo(new UserVO(customStatement.getId(), userPasswordVO.getUsername(), userPasswordVO.getEmail())).getData());
        if (user == null) {
            return CommonResult.fail("用户名或邮箱填写有误");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 验证用户的密码是否正确
        if (passwordEncoder.matches(userPasswordVO.getOldPassword(), user.getPassword())) {
            User newUser = new User();
            // 加密后存储
            String encodePwd = passwordEncoder.encode(userPasswordVO.getNewPassword());
            newUser.setPassword(encodePwd);
            newUser.setId(customStatement.getId());
            CommonResult result = userServiceClient.updateUserInfo(newUser);
            // 修改成功
            if (result.getCode() == 200) {
                return CommonResult.success("密码修改成功");
            } else {
                return CommonResult.fail("用户信息填写有误");
            }
        }
        return CommonResult.fail(StatusEnum.PASSWORD_WRONG, StatusEnum.PASSWORD_WRONG.getMsg());
    }

    @Override
    public CommonResult<User> getUserInfo(CustomStatement customStatement) {
        User user = Convert.convert(User.class, userServiceClient.queryUserById(customStatement.getId()).getData());
        if (user == null) {
            return new CommonResult<>(StatusEnum.USER_UNEXISTED.getCode(), "用户信息不存在");
        }
        return CommonResult.success(user, "用户查询成功");
    }


    /**
     * 验证用户的密码是否符合要求，数据库中是否有相同的用户名和邮箱
     * @param user 用户对象
     * @return 如果返回的对象不为空，则表明不符合指定要求，反之则满足条件
     */
    private Optional<CommonResult> verifyUser(User user) {
        // 密码不符合要求
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
     * 验证用户输入的验证码是否正确
     * @param redisKey 存放验证码对应的key
     * @param verifyCode 用户输入的验证码
     * @return 验证的结果
     */
    private CommonResult checkVerifyCode(String redisKey, String verifyCode) {
        Boolean captchaEnabled = Convert.convert(Boolean.class, adminServiceClient.getCaptchaEnabled().getData());
        if (captchaEnabled) {
            // 获取redis中的验证码
            String code = (String) normalServiceClient.getInfoByKey(redisKey).getData();
            if (code == null) {
                return new CommonResult<>(StatusEnum.VERIFY_ERROR.getCode(), "验证码过期");
            }
            if (code.equalsIgnoreCase(verifyCode)) {
                return CommonResult.success("验证成功");
            } else {
                return new CommonResult<>(StatusEnum.VERIFY_ERROR.getCode(), "验证码错误");
            }
        } else {
            return CommonResult.success("无需验证");
        }
    }

    /**
     * 处理用户发送的重置密码请求
     * @param resetPasswordVO
     * @return
     */
    @Override
    public CommonResult resetPassword(ResetPasswordVO resetPasswordVO) {
        LoginVO loginVO = new LoginVO();
        loginVO.setEmail(resetPasswordVO.getEmail());
        // 根据邮箱获取用户信息
        User user = (User) userServiceClient.queryUserByNameOrEmail(loginVO).getData();
        if (user == null) {
            return CommonResult.fail("邮箱账号错误");
        }
        // 将用户数据暂时存储到redis
        String key = (String) normalServiceClient.cacheInfo(new CacheInfoVO(user, 30L, TimeUnit.MINUTES, CacheConstants.RESET_PASSWORD_CACHE_USER_KEY)).getData();
        boolean isSuccess = mailHandler.sendResetPasswordVerify(user, key);
        if (isSuccess) {
            return new CommonResult(StatusEnum.SUCCESS.getCode(), "发送重置密码邮件成功");
        } else {
            return new CommonResult(StatusEnum.MAIL_ERROR.getCode(), "邮件发送失败");
        }
    }

    /**
     * 重置用户密码
     * @param resetPasswordVO
     * @return 重置的结果
     */
    @Override
    public CommonResult doResetPassword(ResetPasswordVO resetPasswordVO) {
        if (resetPasswordVO.getKey() == null) {
            throw new ParameterException("参数异常");
        }
        // 在redis中获取用户信息
        User user = Convert.convert(User.class, normalServiceClient.getInfoByKey(resetPasswordVO.getKey()).getData());
        if (user == null) {
            return new CommonResult(StatusEnum.USER_UNEXISTED.getCode(), "重置密码时间已过期");
        }
        if (user.getEmail().equals(resetPasswordVO.getEmail()) && user.getUsername().equals(resetPasswordVO.getUsername())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePwd = passwordEncoder.encode(resetPasswordVO.getPassword());
            user.setPassword(encodePwd);
            UserFactory userFactory = new UserFactory();
            User newUser = userFactory.build(user);
            // 重置密码
            CommonResult res =  userServiceClient.updateUserInfo(newUser);
            if (Objects.equals(res.getCode(), StatusEnum.SUCCESS.getCode())) {
                return CommonResult.success("重置密码成功");
            } else {
                return new CommonResult(StatusEnum.PARAM_ERROR.getCode(), "重置密码失败");
            }
        } else {
            return new CommonResult(StatusEnum.PARAM_ERROR.getCode(), "参数传递错误");
        }
    }

    @Override
    public int getAllUserNum() {
        Integer count = (Integer) userServiceClient.querySystemUserCount().getData();
        return count;
    }

    /**
     * 校验登录的用户的用户名是否在黑名单中
     * @return
     */
    private Boolean validateLoginUsername(String username) {
        // 用户名黑名单校验
        String blackStr = Convert.convert(String.class, adminServiceClient.getBlockUsernameList().getData());
        if (StringUtils.isEmpty(blackStr) || StringUtils.isEmpty(username)) {
            return  false;
        }
        String[] blackUsernameList = blackStr.split(";");
        for (String backUsername : blackUsernameList) {
            if (backUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    private void recordLoginLog(final String username, final String status, final String message,
                                final Object... args)
    {
        // 获取request对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        StringBuilder s = new StringBuilder();
        s.append(getBlock(username));
        s.append(getBlock(status));
        s.append(getBlock(message));
        // 打印信息到日志
        sys_user_logger.info(s.toString(), args);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);
        loginLog.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, "Success", "Logout", "Register")) {
            loginLog.setStatus("0");
        } else if ("Error".equals(status)) {
            loginLog.setStatus("1");
        }
        // 插入数据
        normalServiceClient.addLoginLog(loginLog);
    }

    /**
     * 处理并记录日志文件
     * @param msg
     * @return
     */
    private String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
