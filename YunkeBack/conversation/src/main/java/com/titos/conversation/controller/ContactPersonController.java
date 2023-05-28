package com.titos.conversation.controller;

import com.titos.conversation.service.ContactPersonService;
import com.titos.info.global.CommonResult;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/conversation/contactperson")
public class ContactPersonController {
    @Resource
    private ContactPersonService contactPersonService;

    /**
     * 添加好友
     * @param toId 被加好友的id
     */
    @InjectToken
    @PostMapping("/addFriend")
    public CommonResult<Boolean> addFriend(CustomStatement customStatement, Integer toId) {
        Integer userId = customStatement.getId();
        int cnt = contactPersonService.addFriend(userId, toId);
        if(cnt == 0) {
            return CommonResult.fail(false);
        }
        return CommonResult.success(true);
    }

}
