package com.titos.personalmanagement.service.impl;

import com.titos.info.file.FileInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.user.entity.UserInfoDomain;
import com.titos.info.user.query.LoginQuery;
import com.titos.info.user.query.RegisterQuery;
import com.titos.info.user.query.ResetPasswordQuery;
import com.titos.info.user.query.UserPassword;
import com.titos.info.user.vo.LoginVo;
import com.titos.personalmanagement.cache.userinfo.UserInfoCache;
import com.titos.personalmanagement.cache.verifycode.VerifyCodeCache;
import com.titos.personalmanagement.config.YkSysConf;
import com.titos.personalmanagement.convert.RegisterQueryConvert;
import com.titos.personalmanagement.convert.UserConvert;
import com.titos.personalmanagement.dao.UserDao;
import com.titos.personalmanagement.factory.UserFactory;
import com.titos.personalmanagement.model.User;
import com.titos.personalmanagement.mail.MailHandler;
import com.titos.personalmanagement.service.UserService;
import com.titos.rpc.file.FileRpc;
import com.titos.tool.check.VerifyPasswordUtil;
import com.titos.tool.check.VerifyStringUtil;
import com.titos.tool.exception.ParameterException;
import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenContent;
import com.titos.tool.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 操作用户数据的service层
 * @author Titos
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private YkSysConf ykSysConf;
    @Resource
    private UserInfoCache userInfoCache;
    @Resource
    private VerifyCodeCache verifyCodeCache;
    @Resource
    private MailHandler mailHandler;
    @Resource
    private FileRpc fileRpc;

    @Override
    public CommonResult register(RegisterQuery registerQuery, String redisKey) {
//        CommonResult commonResult = checkVerifyCode(redisKey, registerQuery.getVerifyCode());
//        if (!StatusEnum.SUCCESS.getCode().equals(commonResult.getCode())) {
//            return commonResult;
//        }
        User user = RegisterQueryConvert.convert(registerQuery);
        // 验证用户密码是否符合要求，用户名或邮箱是否存在
//        Optional<CommonResult> res = verifyUser(user);
        // 不满足，直接返回结果
//        if (res.isPresent()) {
//            return res.get();
//        }
        // 如果开启了邮箱注册
        if (ykSysConf.getEnableMailRegister()) {
            // 将用户数据暂时存储到redis
            String key = userInfoCache.cacheInfo(user);

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
        userDao.addNewUser(newUser);
        return newUser;
    }

    /**
     * 根据username查询数据库中是否已经存在数据
     * @param username 用户名
     * @return 查询结果
     */
    @Override
    public boolean isUsernameExisted(String username) {
        User userQuery = new User();
        userQuery.setUsername(username);
        Integer userId = userDao.selectIdDynamic(userQuery);
        return userId != null;
    }
    /**
     * 根据email查询数据库中是否已经存在数据
     * @param email 邮箱
     * @return 查询结果
     */
    @Override
    public boolean isEmailExisted(String email) {
        User userQuery = new User();
        userQuery.setEmail(email);
        Integer userId = userDao.selectIdDynamic(userQuery);
        return userId != null;
    }

    @Override
    public CommonResult<LoginVo> login(LoginQuery loginQuery, String redisKey) {
        CommonResult commonResult = checkVerifyCode(redisKey, loginQuery.getVerifyCode());
        if (!StatusEnum.SUCCESS.getCode().equals(commonResult.getCode())) {
            return commonResult;
        }
        // 根据前端传递的参数查找数据库中的数据
        User user = userDao.selectUserToLogin(loginQuery);
        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 如果存在该用户
            if (passwordEncoder.matches(loginQuery.getPassword(), user.getPassword())) {
                // JWT的payload中的自定义的私有字段
                CustomStatement customStatement = new CustomStatement();
                customStatement.setId(user.getId());
                customStatement.setUsername(user.getUsername());
                customStatement.setRole(user.getPersonType());
                TokenContent tokenContent = new TokenContent(customStatement, ykSysConf.getTokenSecretKey());
                String token = TokenUtil.buildToken(tokenContent);
                LoginVo loginVo = new LoginVo(token, user.getId(), user.getPersonType(), user.getUsername(), user.getHeadImage());
                return new CommonResult<>(StatusEnum.SUCCESS.getCode(), loginVo, "登录成功");
            } else {
                return new CommonResult<>(StatusEnum.PASSWORD_WRONG.getCode(), "用户名或密码错误");
            }
        }
        return new CommonResult<>(StatusEnum.USER_UNEXISTED.getCode(), "用户名或密码错误");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public CommonResult verifyEmail(String username, String key) {
        User user = userInfoCache.getInfoByKey(key);
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
     * @param userInfoDomain 接收用户参数封装的实体类
     * @param customStatement 用户在token中的信息
     * @return 修改后的用户信息
     */
    @Override
    public CommonResult<UserInfoDomain> modifyUserInfo(UserInfoDomain userInfoDomain, CustomStatement customStatement) {
        User user = UserConvert.toModel(userInfoDomain);
        userDao.updateUserInfoByIdSelective(user);
        User userInfo = userDao.selectUserInfoById(user.getId());
        userInfoDomain = UserConvert.toDomain(userInfo);
        return CommonResult.success(userInfoDomain, "修改用户信息");
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
        CommonResult<FileInfo> res = fileRpc.upload(image);
        if (!StatusEnum.SUCCESS.getCode().equals(res.getCode())) {
            return new CommonResult<>(StatusEnum.FILE_SIZE_ERROR.getCode(), "文件存储失败");
        }
        User user = new User();
        user.setId(customStatement.getId());
        user.setHeadImage(res.getData().getUrl());
        userDao.updateUserInfoByIdSelective(user);
        return CommonResult.success(res.getData().getUrl(), "修改成功");
    }

    @Override
    public CommonResult modifyUserPassword(CustomStatement customStatement, UserPassword userPassword) {
        // 根据用户id获取用户的信息
        User user = userDao.selectUserForUpdate(customStatement.getId(), userPassword.getUsername(), userPassword.getEmail());
        if (user == null) {
            return CommonResult.fail("用户名或邮箱填写有误");
        }
        User userQuery = new User();
        userQuery.setId(customStatement.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 验证用户的密码是否正确
        if (passwordEncoder.matches(userPassword.getOldPassword(), user.getPassword())) {
            User newUser = new User();
            // 加密后存储
            String encodePwd = passwordEncoder.encode(userPassword.getNewPassword());
            newUser.setPassword(encodePwd);
            newUser.setId(customStatement.getId());
            int res = userDao.updateUserInfoByIdSelective(newUser);
            // 修改成功
            if (res == 1) {
                return CommonResult.success("密码修改成功");
            } else {
                return CommonResult.fail("用户信息填写有误");
            }
        }
        return CommonResult.fail(StatusEnum.PASSWORD_WRONG, StatusEnum.PASSWORD_WRONG.getMsg());
    }

    @Override
    public CommonResult<UserInfoDomain> getUserInfo(CustomStatement customStatement) {
        User user = userDao.selectUserInfoById(customStatement.getId());
        if (user == null) {
            return new CommonResult<>(StatusEnum.USER_UNEXISTED.getCode(), "用户信息不存在");
        }
        UserInfoDomain userInfoDomain = UserConvert.toDomain(user);
        return CommonResult.success(userInfoDomain, "用户查询成功");
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
        // 获取redis中的验证码
        String code = verifyCodeCache.getCodeByKey(redisKey);
        if (code == null) {
            return new CommonResult<>(StatusEnum.VERIFY_ERROR.getCode(), "验证码过期");
        }
        if (code.equalsIgnoreCase(verifyCode)) {
            return CommonResult.success("验证成功");
        } else {
            return new CommonResult<>(StatusEnum.VERIFY_ERROR.getCode(), "验证码错误");
        }
    }

    /**
     * 处理用户发送的重置密码请求
     * @param resetPasswordQuery
     * @return
     */
    @Override
    public CommonResult resetPassword(ResetPasswordQuery resetPasswordQuery) {
        LoginQuery loginQuery = new LoginQuery();
        loginQuery.setEmail(resetPasswordQuery.getEmail());
        // 根据邮箱获取用户信息
        User user = userDao.selectUserToLogin(loginQuery);
        if (user == null) {
            return CommonResult.fail("邮箱账号错误");
        }
        // 将用户数据暂时存储到redis
        String key = userInfoCache.cacheInfo(user);
        boolean isSuccess = mailHandler.sendResetPasswordVerify(user, key);
        if (isSuccess) {
            return new CommonResult(StatusEnum.SUCCESS.getCode(), "发送重置密码邮件成功");
        } else {
            return new CommonResult(StatusEnum.MAIL_ERROR.getCode(), "邮件发送失败");
        }
    }

    /**
     * 重置用户密码
     * @param resetPasswordQuery
     * @return 重置的结果
     */
    @Override
    public CommonResult doResetPassword(ResetPasswordQuery resetPasswordQuery) {
        if (resetPasswordQuery.getKey() == null) {
            throw new ParameterException("参数异常");
        }
        // 在redis中获取用户信息
        User user = userInfoCache.getInfoByKey(resetPasswordQuery.getKey());
        if (user == null) {
            return new CommonResult(StatusEnum.USER_UNEXISTED.getCode(), "重置密码时间已过期");
        }
        if (user.getEmail().equals(resetPasswordQuery.getEmail()) && user.getUsername().equals(resetPasswordQuery.getUsername())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePwd = passwordEncoder.encode(resetPasswordQuery.getPassword());
            user.setPassword(encodePwd);
            UserFactory userFactory = new UserFactory();
            User newUser = userFactory.build(user);
            // 重置密码
            int res = userDao.updateUserInfoByIdSelective(newUser);
            if (res == 1) {
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
        return userDao.getSystemUserCount();
    }
}
