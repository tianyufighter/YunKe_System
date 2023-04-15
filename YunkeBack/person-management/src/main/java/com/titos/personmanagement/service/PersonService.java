package com.titos.personmanagement.service;

import com.titos.info.global.CommonResult;
import com.titos.info.personmanagement.vo.LoginSuccessVO;
import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.info.user.model.User;
import com.titos.personmanagement.query.LoginQuery;
import com.titos.personmanagement.vo.*;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户个人登录、退出、个人信息管理操作
 * @author Titos
 */
public interface PersonService {
    /**
     * 是否开启验证码功能
     * @return
     */
    boolean isCaptchaEnabled();
    /**
     * 注册用户
     * @param registerVO
     * @param redisKey 存放验证码的key
     * @return 状态信息
     */
    CommonResult register(RegisterVO registerVO, String redisKey);

    /**
     * 检验用户名是否存在
     * @param username 用户名
     * @return 检验结果
     */
    boolean isUsernameExisted(String username);

    /**
     * 检验邮箱是否存在
     * @param email 邮箱
     * @return 检验结果
     */
    boolean isEmailExisted(String email);

    /**
     * 登录操作
     * @param loginVO
     * @param redisKey 存放验证码的key
     * @return
     */
    CommonResult<LoginSuccessVO> login(LoginVO loginVO, String redisKey);

    /**
     * 注册时邮箱验证操作
     * @param username 用户名
     * @param key redis中用户的key
     * @return 验证的结果
     */
    CommonResult<LoginQuery> verifyEmail(String username, String key);

    /**
     * 修改用户信息
     * @param user 接收用户参数封装的实体类
     * @param customStatement 用户在token中的信息
     * @return 修改后的用户信息
     */
    CommonResult<User> modifyUserInfo(User user, CustomStatement customStatement);

    /**
     * 修改用户头像
     * @param image 用户头像文件
     * @param customStatement token中用户的信息部分
     * @return 保存成功后的用户图片在服务器上的url地址
     */
    CommonResult<String> modifyUserAvatar(MultipartFile image, CustomStatement customStatement);

    /**
     * 修改用户密码
     * @param customStatement token中用户的信息部分
     * @param userPasswordVO 用户密码的封装类
     * @return 修改的结果
     */
    CommonResult modifyUserPassword(CustomStatement customStatement, UserPasswordVO userPasswordVO);

    /**
     * 获取用户的所有信息（除密码）
     * @param customStatement token中的用户自定义信息
     * @return 用户的信息
     */
    CommonResult<User> getUserInfo(CustomStatement customStatement);

    /**
     * 处理用户发送的重置密码请求
     * @param resetPasswordVO
     * @return
     */
    CommonResult resetPassword(ResetPasswordVO resetPasswordVO);
    /**
     * 重置用户密码
     * @param resetPasswordVO
     * @return 重置的结果
     */
    CommonResult doResetPassword(ResetPasswordVO resetPasswordVO);

    /**
     * 获取总的系统人数
     * @return 人数
     */
    int getAllUserNum();
}
