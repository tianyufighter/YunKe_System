package com.titos.conversation.controller;

import com.titos.conversation.po.MessagePO;
import com.titos.conversation.service.ConversationService;
import com.titos.conversation.utils.CheckUtil;
import com.titos.conversation.vo.SimpleInformationVO;
import com.titos.info.global.CommonResult;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/1 20:47
 * @Version: 1.0.0
 * @Description: TODO
 */
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    ConversationService service;

    /**
     * 返回与被点击头像私信用户的所有历史聊天信息。
     * @param toId 被点击用户的 toId
     * TODO：没有实现分页，redis
     */
    @InjectToken
    @PostMapping("/getAllMessage")
    public CommonResult<List<MessagePO>> getAllMessage(CustomStatement customStatement, @RequestParam String toId) {
        Integer userId = customStatement.getId();
        Integer otherUserId = Integer.parseInt(toId);
        List<MessagePO> result = service.selectAllDialog(userId, otherUserId);
        if(result == CheckUtil.defaultErrorMessagePoList) {
            return CommonResult.fail();
        }
        return CommonResult.success(result);
    }

    /**
     * 删除两个用户交流的所有信息
     * @param toId 被删除的用户的 id
     */
    @InjectToken
    @PostMapping("/deleteDialog")
    public CommonResult<Boolean> deleteDialog(CustomStatement customStatement, String toId) {
        Integer userId = customStatement.getId();
        Integer otherUserId = Integer.parseInt(toId);
        int cnt = service.deleteDialog(userId, otherUserId);
        if(cnt == -1) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 添加好友
     * @param toId 被加好友的id
     */
    @InjectToken
    @PostMapping("/addFriend")
    public CommonResult<Boolean> addFriend(CustomStatement customStatement, String toId) {
        Integer userId = customStatement.getId();
        Integer otherUserId = Integer.parseInt(toId);
        int cnt = service.addFriend(userId, otherUserId);
        if(cnt == -1) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 得到当前用户的所有好友的聊天简况
     * @param customStatement
     * @return
     */
    @InjectToken
    @PostMapping("/getSimpleInformation")
    public CommonResult<List<SimpleInformationVO>> getSimpleInformationVO(CustomStatement customStatement) {
        List<SimpleInformationVO> simpleInformationVO = service.getSimpleInformation(customStatement.getId());
        if(simpleInformationVO == CheckUtil.defaultErrorSimpleInformationVO) {
            return CommonResult.fail();
        }
        return CommonResult.success(simpleInformationVO);
    }

    /**
     * 获取toId 在id 离线时发送的信息
     * @param customStatement
     * @param toId
     * @return
     */
    @InjectToken
    @PostMapping("/getAllDialogReceiveNotComplete")
    public CommonResult<List<MessagePO>> getAllDialogReceiveNotComplete(CustomStatement customStatement, Integer toId) {
        // 从数据库里面拿看是否有 toId 向 id 发送过离线消息，按时间顺序从小到大
        List<MessagePO> messagePOList = service.selectAllDialogReceiveNotComplete(toId, customStatement.getId());
        if(messagePOList == CheckUtil.defaultErrorMessagePoList) {
            return CommonResult.fail();
        }
        return CommonResult.success(messagePOList);
    }

    @InjectToken
    @PostMapping("/setComplete")
    public CommonResult<Boolean> setComplete(CustomStatement customStatement, Integer toId, String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, df);
        int cnt = service.updateCompleteByChange(customStatement.getId(), toId, localDateTime);
        if(cnt == -1) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

}
