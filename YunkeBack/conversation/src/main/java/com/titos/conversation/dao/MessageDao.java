package com.titos.conversation.dao;

import com.titos.conversation.model.Message;
import com.titos.conversation.vo.SimpleInformationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface MessageDao {
    /**
     * 查询两个人的对话
     * @param sendId 发送者的id
     * @param receiveId 接收者的id
     * @return 返回所有message表中两人的记录
     * @throws DataAccessException 数据访问错误
     */
    List<Message> selectAllMessage(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 删除两个人所对应的聊天信息
     * @param sendId 发送者的id
     * @param receiveId 接收者的id
     * @return 删除的结果
     * @throws DataAccessException 数据访问错误
     */
    Integer deleteMessage(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 插入聊天信息
     * @param message
     * @return
     */
    Integer insertMessage(Message message);

    /**
     * 查询receiver还未接收到的消息
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    List<Message> selectNotReceiveMessage(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 更新消息的接收
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    Integer updateComplete(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 返回当前好友聊天的简单信息
     * @param id
     * @return
     * @throws DataAccessException
     */
    List<SimpleInformationVO> selectSimpleInformation(Integer id);
}
