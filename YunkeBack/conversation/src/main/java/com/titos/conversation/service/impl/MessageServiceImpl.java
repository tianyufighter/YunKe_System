package com.titos.conversation.service.impl;

import com.titos.conversation.dao.ContactPersonDao;
import com.titos.conversation.dao.MessageDao;
import com.titos.conversation.model.ContactPerson;
import com.titos.conversation.model.Message;
import com.titos.conversation.service.MessageService;
import com.titos.conversation.vo.SimpleInformationVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;
    @Resource
    private ContactPersonDao contactPersonDao;
    @Transactional
    @Override
    public List<Message> queryAllChatMessage(Integer sendId, Integer receiveId) {
        List<Message> messages = messageDao.selectAllMessage(sendId, receiveId);
        Integer count = messageDao.updateComplete(sendId, receiveId);
        return messages;
    }
    @Transactional
    @Override
    public Integer deleteChatMessage(Integer sendId, Integer receiveId) {
        contactPersonDao.deleteFriend(sendId, receiveId);
        int cnt = messageDao.deleteMessage(sendId, receiveId);
        return cnt;
    }

    @Transactional
    @Override
    public Integer sendChatMessage(Message message) {
        int cnt = messageDao.insertMessage(message);
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setUserId(message.getSendId());
        contactPerson.setFriendId(message.getReceiveId());
        contactPerson.setMessageId(message.getId());
        contactPersonDao.updateMessageLatest(contactPerson);
        return cnt;
    }

    @Transactional
    @Override
    public List<Message> queryNotReceiveMessage(Integer sendId, Integer receiveId) {
        List<Message> list = messageDao.selectNotReceiveMessage(sendId, receiveId);
        messageDao.updateComplete(sendId, receiveId);
        return list;
    }

    @Override
    public Integer markCompleteMessage(Integer sendId, Integer receiveId) {
        int res = messageDao.updateComplete(sendId, receiveId);
        return res;
    }

    @Override
    public List<SimpleInformationVO> getSimpleInformation(Integer id) {
        List<SimpleInformationVO> simpleInformationVO = messageDao.selectSimpleInformation(id);
        return simpleInformationVO;
    }
}
