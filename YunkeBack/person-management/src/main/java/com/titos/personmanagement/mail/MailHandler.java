package com.titos.personmanagement.mail;


import com.titos.info.user.model.User;

/**
 * 邮箱操作
 * @author Titos
 */
public interface MailHandler {
    /**
     * 发送注册时的验证邮件
     * @param user 用户对象
     * @param key 用户对象在redis中对应的key
     * @return 发送的结果
     */
    boolean sendAccountVerify(User user, String key);

    /**
     * 发送重置密码的验证邮件
     * @param user 用户对象
     * @param key 用户对象在redis中对应的key
     * @return 发送的结果
     */
    boolean sendResetPasswordVerify(User user, String key);
}
