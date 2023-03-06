package com.titos.conversation.dao;

import com.titos.conversation.po.MessagePO;
import com.titos.conversation.vo.SimpleInformationVO;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/2 23:44
 * @Version: 1.0.0
 * @Description:
 */
public interface ConversationDao {
    /**
     * 查询两个人的对话
     * @param id 发送者的id
     * @param otherId 接收者的id
     * @return 返回所有message表中两人的记录
     * @throws DataAccessException 数据访问错误
     */
    List<MessagePO> selectAllDialog(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 删除两个人的所有聊天信息和好友关系信息
     * @param id 发起者id
     * @param otherId 被删除者id
     * @return 返回删除的个数
     * @throws DataAccessException 数据访问错误
     */
    int deleteDialog(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 插入聊天信息
     * @param messagePO
     * @return 返回插入条数
     * @throws DataAccessException 数据访问错误
     */
    int insertDialog(MessagePO messagePO) throws DataAccessException;

    /**
     * 建立两个好友之间的关系
     * @param id 发起者id
     * @param otherId 接受者id
     * @return 返回插入结果
     * @throws DataAccessException 数据访问错误
     */
    int insertFriend(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 删除两个好友之间的联系
     * @param id 删除者id
     * @param otherId 被删除者id1
     * @return 返回删除的个数
     * @throws DataAccessException 数据访问错误
     */
    int deleteFriend(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 返回otherId没有接收到id 的所有信息
     * @param id id
     * @param otherId otherId
     * @return 返回信息
     * @throws DataAccessException
     */
    List<MessagePO> selectAllDialogReceiveNotComplete(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 更新消息的接收
     * @param id 接收者
     * @param otherId 发送者
     * @return
     * @throws DataAccessException
     */
    int updateComplete(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 查询两个人是否是好友关系
     * @param id
     * @param otherId
     * @return
     * @throws DataAccessException
     */
    int selectFriend(Integer id, Integer otherId) throws DataAccessException;

    /**
     * 更新两个好友间的最近消息
     * @param id
     * @param otherId
     * @param maxId
     * @return
     * @throws DataAccessException
     */
    int updateFriend(Integer id, Integer otherId, Integer maxId) throws DataAccessException;

    /**
     * 返回当前好友聊天的简单信息
     * @param id
     * @return
     * @throws DataAccessException
     */
    List<SimpleInformationVO> selectSimpleInformation(Integer id) throws DataAccessException;

    /**
     * 更新为unread
     * @param id
     * @param otherId
     * @param time
     * @return
     * @throws DataAccessException
     */
    int updateCompleteByChange(Integer id, Integer otherId, LocalDateTime time) throws DataAccessException;

}
