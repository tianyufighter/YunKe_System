package com.titos.conversation.service.impl;

import com.titos.conversation.dao.ContactPersonDao;
import com.titos.conversation.model.ContactPerson;
import com.titos.conversation.service.ContactPersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
public class ContactPersonServiceImpl implements ContactPersonService {
    @Resource
    private ContactPersonDao contactPersonDao;
    @Transactional
    @Override
    public Integer addFriend(Integer sendId, Integer receiveId) {
        int cnt = 0;
        if (contactPersonDao.selectIsFriend(sendId, receiveId) == 0) {
            cnt = contactPersonDao.insertFriend(sendId, receiveId);
        }
        return cnt;
    }

    @Override
    public Boolean isFriend(Integer sendId, Integer receiveId) {
        int cnt = contactPersonDao.selectIsFriend(sendId, receiveId);
        if (cnt == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer updateMessageLatest(ContactPerson contactPerson) {
        Integer res = contactPersonDao.updateMessageLatest(contactPerson);
        return res;
    }
}
