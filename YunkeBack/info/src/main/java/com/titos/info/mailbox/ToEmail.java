package com.titos.info.mailbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送邮件的封装类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToEmail {
    /**
     * 邮件接收方，可多人
     */
    private String[] tos;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
}
