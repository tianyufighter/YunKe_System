package com.titos.conversation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContactPerson {
    /**
     * 主键id
     */
    private Integer id;
    private Integer userId;
    private Integer friendId;
    /**
     * 最后一次发送消息所对应的信息id
     */
    private Integer messageId;
}
