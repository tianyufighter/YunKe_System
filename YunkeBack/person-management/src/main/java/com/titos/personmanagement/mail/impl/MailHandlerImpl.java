package com.titos.personmanagement.mail.impl;

import com.titos.info.mailbox.ToEmail;
import com.titos.info.user.model.User;
import com.titos.personmanagement.config.YkSysConf;
import com.titos.personmanagement.mail.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 发送邮件的实现类
 * @author Titos
 */
@Repository
public class MailHandlerImpl implements MailHandler {
    @Autowired
    private YkSysConf ykSysConf;
    @Resource
    private JavaMailSender mailSender;
    @Override
    public boolean sendAccountVerify(User user, String key) {
        // 获取username和用户的email
        String username = user.getUsername();
        String email = user.getEmail();
        ToEmail toEmail = createPostEmail(new String[]{email}, key, username);
        // 创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mineMessageHelper = new MimeMessageHelper(message, true);
            // 设置发送者
            mineMessageHelper.setFrom(ykSysConf.getMailSender());
            // 设置接收者
            mineMessageHelper.setTo(toEmail.getTos());
            // 邮件主题
            mineMessageHelper.setSubject(toEmail.getSubject());
            // 邮件内容 true表示带有附件或html
            mineMessageHelper.setText(toEmail.getContent(), true);
            try {
                mailSender.send(message);
                return true;
            } catch (MailException e) {
                e.printStackTrace();
                return false;
            }
        } catch (MessagingException e) {
            throw new RuntimeException("error when preparing to send mail");
        }
    }

    @Override
    public boolean sendResetPasswordVerify(User user, String key) {
        // 获取username和用户的email
        String username = user.getUsername();
        String email = user.getEmail();
        ToEmail toEmail = createPasswordEmail(new String[]{email}, key, username);
        // 创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mineMessageHelper = new MimeMessageHelper(message, true);
            // 设置发送者
            mineMessageHelper.setFrom(ykSysConf.getMailSender());
            // 设置接收者
            mineMessageHelper.setTo(toEmail.getTos());
            // 邮件主题
            mineMessageHelper.setSubject(toEmail.getSubject());
            // 邮件内容 true表示带有附件或html
            mineMessageHelper.setText(toEmail.getContent(), true);
            try {
                mailSender.send(message);
                return true;
            } catch (MailException e) {
                e.printStackTrace();
                return false;
            }
        } catch (MessagingException e) {
            throw new RuntimeException("error when preparing to send mail");
        }
    }

    /**
     * 创建发送邮件的对象
     * @param receiver 接收者
     * @param key redis中存储用户的键
     * @param username 用户名
     * @return 发送邮件对象
     */
    private ToEmail createPostEmail(String[] receiver, String key, String username) {
        // 获取ip地址
        String host = ykSysConf.getFrontEndUrl();
        String url = "http://"+host+"/personManagement/verifyEmail/"+key+"/" + username ;
        // 当前发送邮件的时间
        String sendTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        String subject = "云客系统注册平台验证";
        String content = "<html>\n" +
                "<body>\n" +
                "    <div>亲爱的"+username+"你好!你于"+sendTime+"在云客平台发起了注册，如果是你本人需要点击<a href=\""+url+"\">YUNKE验证平台</a>来验证是否你的本人。(有效时间30分钟)</div>\n" +
                "</body>\n" +
                "</html>";
        return new ToEmail(receiver, subject, content);
    }

    /**
     * 创建发送重置密码的邮件的内容
     * @param receiver 接收者
     * @param key redis中存储用户的key
     * @param username 用户名
     * @return 发送邮件对象
     */
    private ToEmail createPasswordEmail(String[] receiver, String key, String username) {
        // 获取ip地址
        String host = ykSysConf.getFrontHostUrl();
        String url = "http://"+host+"/pages/reset-password?key="+key + "&username="+username;
        // 当前发送邮件的时间
        String sendTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        String subject = "云客系统重置密码平台验证";
        String content = "<html>\n" +
                "<body>\n" +
                "    <div>亲爱的"+username+"你好!你于"+sendTime+"在云客平台发起了找回密码请求，如果是你本人需要点击<a href=\""+url+"\">YUNKE重置密码平台</a>来重置你的密码。(有效时间30分钟)</div>\n" +
                "</body>\n" +
                "</html>";
        return new ToEmail(receiver, subject, content);
    }
}
