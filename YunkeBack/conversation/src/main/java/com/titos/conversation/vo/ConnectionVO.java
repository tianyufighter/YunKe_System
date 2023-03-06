package com.titos.conversation.vo;

import lombok.Data;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/8 10:42
 * @Version: 1.0.0
 * @Description:
 */
@Data
public class ConnectionVO {
    Boolean isSystem;
    Integer fromId;
    Integer toId;
    String message;
}
