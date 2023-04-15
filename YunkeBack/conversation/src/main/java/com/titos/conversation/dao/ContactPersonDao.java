package com.titos.conversation.dao;

import com.titos.conversation.model.ContactPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContactPersonDao {
    /**
     * 建立两个好友之间的关系
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    Integer insertFriend(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 删除两个好友之间的关系
     * @param sendId 发送者id
     * @param receiveId 接收id
     * @return
     */
    Integer deleteFriend(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 查询两个人是否是好友关系
     * @param sendId 发送者id
     * @param receiveId 接收者id
     * @return
     */
    Integer selectIsFriend(@Param("sendId") Integer sendId, @Param("receiveId") Integer receiveId);

    /**
     * 更新两个好友的最新消息
     * @param contactPerson
     * @return
     */
    Integer updateMessageLatest(ContactPerson contactPerson);
}
