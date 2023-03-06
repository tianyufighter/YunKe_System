package com.titos.conversation.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/1 20:54
 * @Version: 1.0.0
 * @Description: 对应数据库中的消息表
 */
@Data
public class MessagePO {
    private Integer id;
    private Integer sendId;
    private Integer receiveId;
    private String content;
    private String imageAddr;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime releaseTime;
    private Integer isComplete;
}
