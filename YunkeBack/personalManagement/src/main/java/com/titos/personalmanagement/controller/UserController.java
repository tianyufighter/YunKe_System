package com.titos.personalmanagement.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.titos.info.global.CommonResult;
import com.titos.info.user.entity.UserInfoDomain;
import com.titos.info.user.query.LoginQuery;
import com.titos.info.user.query.RegisterQuery;
import com.titos.info.user.query.ResetPasswordQuery;
import com.titos.info.user.query.UserPassword;
import com.titos.info.user.vo.LoginVo;
import com.titos.info.user.vo.OnlineUserVo;
import com.titos.personalmanagement.cache.verifycode.VerifyCodeCache;
import com.titos.personalmanagement.model.User;
import com.titos.personalmanagement.service.UserService;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.annotions.ParamVerify;
import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 普通用户的登录、注册操作
 * @author Titos
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private VerifyCodeCache verifyCodeCache;

    /**
     * 注册，将用户信息缓存到redis中并发送邮件
     * @param registerQuery
     * @return
     */
    @ParamVerify(notNull = {"registerQuery.username", "registerQuery.email", "registerQuery.password"})
    @PostMapping("/signUp")
    public CommonResult register(@RequestBody RegisterQuery registerQuery, HttpServletRequest request) {
        String redisKey = request.getHeader("Redis-Key");
        return userService.register(registerQuery, redisKey);
    }

    /**
     * 邮箱验证注册的用户
     * @return 验证的结果
     */
    @GetMapping("/verifyEmail/{key}/{username}")
    public CommonResult<LoginVo> signUpVerify(@PathVariable("key") String key, @PathVariable("username") String username) {
        return userService.verifyEmail(username, key);
    }

    /**
     * 登录时，需要验证码验证
     * @param loginQuery 接收前端的参数
     * @return 登录的结果
     */
    @PostMapping("/login")
    public CommonResult<LoginVo> login(@RequestBody LoginQuery loginQuery, HttpServletRequest request) {
        String redisKey = request.getHeader("Redis-Key");
        return userService.login(loginQuery, redisKey);
    }

    /**
     * 根据用户id来修改用户的信息
     * @param customStatement 用户在token中的信息
     * @param userInfoDomain 用户信息（不含密码）的实体类
     * @return 更改后的用户信息
     */
    @InjectToken
    @PostMapping("/modifyInfo")
    public CommonResult<UserInfoDomain> modifyUserInfo(CustomStatement customStatement, @RequestBody UserInfoDomain userInfoDomain) {
        if (userInfoDomain.getId() == null) {
            userInfoDomain.setId(customStatement.getId());
        }
        return userService.modifyUserInfo(userInfoDomain, customStatement);
    }

    /**
     * 获取用户的所有信息
     * @param customStatement token中用户的自定义信息
     * @return 用户信息
     */
    @InjectToken
    @PostMapping("/getUserInfo")
    public CommonResult<UserInfoDomain> getUserInfo(CustomStatement customStatement) {
        return userService.getUserInfo(customStatement);
    }

    /**
     * 根据用户id修改用户头像
     * @param customStatement 用户在token中的信息
     * @param img 头像文件
     * @return 头像的url地址
     */
    @InjectToken
    @PostMapping("/fix/avatar")
    public CommonResult<String> modifyUserAvatar(CustomStatement customStatement, @RequestParam MultipartFile img) {
        return userService.modifyUserAvatar(img, customStatement);
    }


    /**
     * 修改用户密码
     * @param customStatement token中用户的信息
     * @param userPassword 用户密码信息的封装类
     * @return 修改的结果
     */
    @InjectToken
    @PostMapping("/fix/password")
    public CommonResult modifyUserPassword(CustomStatement customStatement, @RequestBody UserPassword userPassword) {
        return userService.modifyUserPassword(customStatement, userPassword);
    }

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 生成验证码字符串
            String code = defaultKaptcha.createText();
            // 存储在redis中的key
            String key = verifyCodeCache.cacheVerifyCode(code);
            BufferedImage image = defaultKaptcha.createImage(code);
            ImageIO.write(image, "jpg", outputStream);
            byte[] bytes = outputStream.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Redis-Key", key);
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
        } catch (Exception exc) {
            exc.printStackTrace();
            // 出错的话跳转到错误页面
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
        } finally {
            servletOutputStream.close();
        }
    }

    @ParamVerify(notNull = {"resetPasswordQuery.email"})
    @PostMapping("/resetPassword")
    public CommonResult resetPassword(@RequestBody ResetPasswordQuery resetPasswordQuery) {
        return userService.resetPassword(resetPasswordQuery);
    }

    @PostMapping("/verifyResetPassword")
    public CommonResult verifyResetPassword(@RequestBody ResetPasswordQuery resetPasswordQuery) {
        return userService.doResetPassword(resetPasswordQuery);
    }

    /**
     * 获取在线人数和系统总人数
     * @return
     */
    @InjectToken
    @GetMapping("/getOnlineCount")
    public CommonResult getOnlineUserCount() {
        // 系统总人数
        int totalNum = userService.getAllUserNum();
        int onlineUserNum = TokenUtil.getOnlineCount();
        OnlineUserVo onlineUserVo = new OnlineUserVo(onlineUserNum, totalNum);
        return CommonResult.success(onlineUserVo);
    }
}
