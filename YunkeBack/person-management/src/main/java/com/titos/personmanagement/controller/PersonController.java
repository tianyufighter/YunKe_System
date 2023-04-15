package com.titos.personmanagement.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.info.global.CommonResult;
import com.titos.info.global.constant.CacheConstants;
import com.titos.info.personmanagement.vo.LoginSuccessVO;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.personmanagement.query.LoginQuery;
import com.titos.personmanagement.service.PersonService;
import com.titos.personmanagement.vo.*;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.annotions.ParamVerify;
import com.titos.tool.cache.redis.RedisCache;
import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 普通用户的登录、注册操作
 * @author Titos
 */
@RestController
@RequestMapping("/personManagement")
public class PersonController {
    @Resource
    private PersonService personService;
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private NormalServiceClient normalServiceClient;
    @Resource
    private RedisCache redisCache;

    /**
     * 注册，将用户信息缓存到redis中并发送邮件
     * @param registerVO
     * @return
     */
    @ParamVerify(notNull = {"registerVO.username", "registerVO.email", "registerVO.password"})
    @PostMapping("/signUp")
    public CommonResult register(@RequestBody RegisterVO registerVO, HttpServletRequest request) {
        String redisKey = request.getHeader("Redis-Key");
        return personService.register(registerVO, redisKey);
    }

    /**
     * 邮箱验证注册的用户
     * @return 验证的结果
     */
    @GetMapping("/verifyEmail/{key}/{username}")
    public CommonResult<LoginQuery> signUpVerify(@PathVariable("key") String key, @PathVariable("username") String username) {
        return personService.verifyEmail(username, key);
    }

    /**
     * 登录时，需要验证码验证
     * @param loginVO 接收前端的参数
     * @return 登录的结果
     */
    @PostMapping("/login")
    public CommonResult<LoginSuccessVO> login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        String redisKey = request.getHeader("Redis-Key");
        return personService.login(loginVO, redisKey);
    }

    /**
     * 根据用户id来修改用户的信息
     * @param customStatement 用户在token中的信息
     * @param user 用户信息（不含密码）的实体类
     * @return 更改后的用户信息
     */
    @InjectToken
    @PostMapping("/modifyInfo")

    public CommonResult<User> modifyUserInfo(CustomStatement customStatement, @RequestBody User user) {
        if (user.getId() == null) {
            user.setId(customStatement.getId());
        }
        return personService.modifyUserInfo(user, customStatement);
    }

    /**
     * 获取用户的所有信息
     * @param customStatement token中用户的自定义信息
     * @return 用户信息
     */
    @InjectToken
    @PostMapping("/getUserInfo")
    public CommonResult<User> getUserInfo(CustomStatement customStatement) {
        return personService.getUserInfo(customStatement);
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
        return personService.modifyUserAvatar(img, customStatement);
    }


    /**
     * 修改用户密码
     * @param customStatement token中用户的信息
     * @param userPasswordVO 用户密码信息的封装类
     * @return 修改的结果
     */
    @InjectToken
    @PostMapping("/fix/password")
    public CommonResult modifyUserPassword(CustomStatement customStatement, @RequestBody UserPasswordVO userPasswordVO) {
        return personService.modifyUserPassword(customStatement, userPasswordVO);
    }

    /**
     * 获取是否开启了验证码功能
     * @return
     */
    @GetMapping("/isCaptchaEnabled")
    public CommonResult getIsCaptchaEnabled() {
        return CommonResult.success(personService.isCaptchaEnabled());
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
            String key = (String) normalServiceClient.cacheInfo(new CacheInfoVO(code, 2L, TimeUnit.MINUTES, CacheConstants.CAPTCHA_CODE_KEY)).getData();
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

    /**
     * 重置密码
     * @param resetPasswordVO
     * @return
     */
    @ParamVerify(notNull = {"resetPasswordVO.email"})
    @PostMapping("/resetPassword")
    public CommonResult resetPassword(@RequestBody ResetPasswordVO resetPasswordVO) {
        return personService.resetPassword(resetPasswordVO);
    }

    @PostMapping("/verifyResetPassword")
    public CommonResult verifyResetPassword(@RequestBody ResetPasswordVO resetPasswordVO) {
        return personService.doResetPassword(resetPasswordVO);
    }

    /**
     * 获取在线人数和系统总人数
     * @return
     */
    @GetMapping("/getOnlineCount")
    public CommonResult getOnlineUserCount() {
        // 系统总人数
        int totalNum = personService.getAllUserNum();
        int onlineUserNum = redisCache.getCacheObject(CacheConstants.ONLINE_PEOPLE_COUNT);
        OnlineUserVO onlineUserVo = new OnlineUserVO(onlineUserNum, totalNum);
        return CommonResult.success(onlineUserVo);
    }
}
