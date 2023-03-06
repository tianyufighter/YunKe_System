package com.titos.conversation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ReceiveMessageVO {
    /**
     * 发送的目的用户
     */
    private Integer toUserId;
    /**
     * 消息
     */
    private String message;
    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;
}
