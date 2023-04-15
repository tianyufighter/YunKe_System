package com.titos.conversation.service;

import com.titos.conversation.model.ContactPerson;

public interface ContactPersonService {
    /**
     * 添加好友
     * @param sendId 添加者
     * @param receiveId 被添加者
     * @return 返回是否添加成功
     */
    Integer addFriend(Integer sendId, Integer receiveId);

    /**
     * 查询两个人是不是好友关系
     * @param sendId 添加者
     * @param receiveId 被添加者
     * @return
     */
    Boolean isFriend(Integer sendId, Integer receiveId);

    /**
     * 更新两个好友的最后聊天消息
     * @param contactPerson
     * @return
     */
    Integer updateMessageLatest(ContactPerson contactPerson);
}
