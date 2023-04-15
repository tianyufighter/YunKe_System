package com.titos.conversation.service;

import com.titos.conversation.model.Message;
import com.titos.conversation.vo.SimpleInformationVO;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {
    /**
     * 先从redis里面获取，再去数据库里面查询
     * 查询两个人的所有对话信息
     * @param sendId 发生者id
     * @param receiveId 接收者id
     * @return 返回两个人的所有对话信息
     */
    List<Message> queryAllChatMessage(Integer sendId, Integer receiveId);

    /**
     * 删除对话
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    Integer deleteChatMessage(Integer sendId, Integer receiveId);

    /**
     * 发送消息
     * @param message
     * @return
     */
    Integer sendChatMessage(Message message);

    /**
     * 先从redis里面获取，再从数据库里面查询
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    List<Message> queryNotReceiveMessage(Integer sendId, Integer receiveId);

    /**
     * 将消息标记为完成
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    Integer markCompleteMessage(Integer sendId, Integer receiveId);

    /**
     * 返回id 对应聊天用户的简单信息
     * @param id id用户所有好友的聊天信息
     * @return
     */
    List<SimpleInformationVO> getSimpleInformation(Integer id);
}
