package com.titos.conversation.service.impl;

import com.titos.conversation.dao.ConversationDao;
import com.titos.conversation.po.MessagePO;
import com.titos.conversation.service.ConversationService;
import com.titos.conversation.utils.CheckUtil;
import com.titos.conversation.vo.SimpleInformationVO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/3 20:11
 * @Version: 1.0.0
 * @Description: TODO 利用websocket 进行传输
 */
@Service
public class ConversationServiceImpl implements ConversationService {

    @Resource
    ConversationDao conversationDao;

    /**
     * 返回所有两个用户的所有聊天信息,失败则返回空
     * TODO 当前用户接收了所有信息，修改另一个用户的所有发送信息is_complete=1
     * @param id 发起者的id
     * @param otherId 接收者的id
     * @return
     */
    @Transactional
    @Override
    public List<MessagePO> selectAllDialog(Integer id, Integer otherId) {
        List<MessagePO> messagePoList = null;
        try {
            messagePoList = conversationDao.selectAllDialog(id, otherId);
            conversationDao.updateComplete(id, otherId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            messagePoList = CheckUtil.defaultErrorMessagePoList;
        }
        return messagePoList;
    }

    /**
     * 删除先把两个好友的联系删除，再删除所有的信息，必须同时成功。
     * @param id 发起者id
     * @param otherId 被删除者id
     * @return
     */
    @Override
    @Transactional
    public int deleteDialog(Integer id, Integer otherId) {
        int cnt;
        try {
            conversationDao.deleteFriend(id, otherId);
            cnt = conversationDao.deleteDialog(id, otherId);
        } catch (DataAccessException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            cnt = -1;
        }
        return cnt;
    }

    /**
     * 发送信息，失败手动
     * @param id 发送者id
     * @param otherId 接收者id
     * @param message 发送的消息
     * @param time 发送时间
     * @param isComplete 是否成功
     * @return
     */
    @Transactional
    @Override
    public int sendDialog(Integer id, Integer otherId, String message, LocalDateTime time, Integer isComplete) {
        int cnt = 0;
        MessagePO messagePO = new MessagePO();
        messagePO.setSendId(id);
        messagePO.setReceiveId(otherId);
        messagePO.setContent(message);
        messagePO.setImageAddr(message);
        messagePO.setIsComplete(isComplete);
        messagePO.setReleaseTime(time);
        try {
            cnt = conversationDao.insertDialog(messagePO);
            conversationDao.updateFriend(id, otherId, messagePO.getId());
        } catch (DataAccessException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            cnt = -1;
        }
        return cnt;
    }

    /**
     * 添加好友
     * TODO 先查询是否加入了连接表里面。
     * @param id 添加者
     * @param otherId 被添加者
     * @return CommonResult<Boolean>
     */
    @Transactional
    @Override
    public int addFriend(Integer id, Integer otherId) {
        int cnt = 0;
        try {
            if(conversationDao.selectFriend(id, otherId) == 0) {
                cnt = conversationDao.insertFriend(id, otherId);
            }
        } catch (DataAccessException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            cnt = -1;
        }
        return cnt;
    }

    /**
     * 返回id 向 otherId 发送的全部消息，并且更新数据库的complete信息
     * @param id 发起者的id
     * @param otherId 接收者的id
     * @return
     */
    @Transactional
    @Override
    public List<MessagePO> selectAllDialogReceiveNotComplete(Integer id, Integer otherId) {
        List<MessagePO> list = null;
        try {
            list = conversationDao.selectAllDialogReceiveNotComplete(id, otherId);
            conversationDao.updateComplete(id, otherId);
        } catch (DataAccessException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            list = CheckUtil.defaultErrorMessagePoList;
        }
        return list;
    }

    @Override
    public int isFriend(Integer id, Integer otherId) {
        int cnt = 0;
        try {
            cnt = conversationDao.selectFriend(id, otherId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            cnt = -1;
        }
        return cnt;
    }

    @Override
    public int updateFriend(Integer id, Integer otherId, Integer maxId) {
        int cnt = 0;
        try {
            cnt = conversationDao.updateFriend(id, otherId, maxId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            cnt = -1;
        }
        return cnt;
    }

    @Override
    public int updateComplete(Integer id, Integer otherId) {
        int cnt = 0;
        try {
            cnt = conversationDao.updateComplete(id, otherId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            cnt = -1;
        }
        return cnt;
    }

    @Override
    public List<SimpleInformationVO> getSimpleInformation(Integer id) {
        List<SimpleInformationVO> simpleInformationVO = null;
        try {
            simpleInformationVO = conversationDao.selectSimpleInformation(id);

        } catch (DataAccessException e) {
            e.printStackTrace();
            simpleInformationVO = CheckUtil.defaultErrorSimpleInformationVO;
        }
        return simpleInformationVO;
    }

    @Override
    public int updateCompleteByChange(Integer id, Integer otherId, LocalDateTime time) {
        int cnt = 0;
        try {
            cnt = conversationDao.updateCompleteByChange(id, otherId, time);
        } catch (DataAccessException e) {
            e.printStackTrace();
            cnt = 0;
        }
        return cnt;
    }

}
