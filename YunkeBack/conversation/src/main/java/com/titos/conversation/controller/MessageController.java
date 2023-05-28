package com.titos.conversation.controller;

import com.titos.conversation.model.Message;
import com.titos.conversation.service.MessageService;
import com.titos.conversation.vo.SimpleInformationVO;
import com.titos.info.global.CommonResult;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/conversation/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * 返回与被点击头像私信用户的所有的历史聊天信息
     * @param customStatement
     * @param toId 被点击用户的toId
     * @return
     */
    @InjectToken
    @PostMapping("/getAllMessage")
    public CommonResult<List<Message>> getAllMessage(CustomStatement customStatement, @RequestParam Integer toId) {
        Integer userId = customStatement.getId();
        List<Message> messages = messageService.queryAllChatMessage(toId, userId);
        if (messages == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(messages);
    }

    /**
     * 删除两个用户交流的所有信息
     * @param toId 被删除的用户的 id
     */
    @InjectToken
    @PostMapping("/deleteDialog")
    public CommonResult<Boolean> deleteChatMessage(CustomStatement customStatement, Integer toId) {
        Integer userId = customStatement.getId();
        Integer cnt = messageService.deleteChatMessage(userId, toId);
        if(cnt == 0) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

    /**
     * 获取toId 在id 离线时发送的信息
     * @param customStatement
     * @param toId
     * @return
     */
    @InjectToken
    @PostMapping("/getAllDialogReceiveNotComplete")
    public CommonResult<List<Message>> getAllDialogReceiveNotComplete(CustomStatement customStatement, Integer toId) {
        // 从数据库里面拿看是否有 toId 向 id 发送过离线消息，按时间顺序从小到大
        List<Message> messages = messageService.queryNotReceiveMessage(customStatement.getId(), toId);
        if(messages == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(messages);
    }

    @InjectToken
    @PostMapping("/setComplete")
    public CommonResult<Boolean> setComplete(CustomStatement customStatement, Integer toId) {
        Integer cnt = messageService.markCompleteMessage(customStatement.getId(), toId);
        if(cnt == 0) {
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
        List<SimpleInformationVO> simpleInformationVO = messageService.getSimpleInformation(customStatement.getId());
        if(simpleInformationVO == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(simpleInformationVO);
    }
}
