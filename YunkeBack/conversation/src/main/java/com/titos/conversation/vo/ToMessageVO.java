package com.titos.conversation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToMessageVO {
    /**
     * 是否是系统消息
     */
    private Boolean isSystem;
    /**
     * 发送者的id
     */
    private Integer fromUserId;
    /**
     * 接收者的id
     */
    private Integer toUserId;
    /**
     * 发送的消息
     */
    private String message;
    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;
}
