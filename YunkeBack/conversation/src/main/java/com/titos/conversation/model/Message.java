package com.titos.conversation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户聊天信息所对应的实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Message {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 消息发送者的id
     */
    private Integer sendId;
    /**
     * 消息接收者的id
     */
    private Integer receiveId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 图片地址
     */
    private String imageAddr;
    /**
     * 消息发送的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime releaseTime;
    /**
     * 是否完成
     */
    private Boolean isComplete;
}
