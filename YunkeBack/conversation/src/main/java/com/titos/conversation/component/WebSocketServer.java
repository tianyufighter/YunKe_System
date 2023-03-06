package com.titos.conversation.component;

import com.alibaba.fastjson.JSONObject;
import com.titos.conversation.config.CustomSpringConfigurator;
import com.titos.conversation.service.ConversationService;
import com.titos.conversation.vo.ReceiveMessageVO;
import com.titos.conversation.vo.ToMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/7 23:30
 * @Version: 1.0.0
 * @Description:
 */
@ServerEndpoint(value = "/conversation/chat/{id}",configurator = CustomSpringConfigurator.class)
@Component
public class WebSocketServer {
    /**
     * 用来存储每一个客户端对象对应的ChatEndpoint对象
     */
    private static ConcurrentHashMap<Integer,Session> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 声明session对象，通过该对象可以发送消息给指定的用户
     */
    private Session session;
    @Autowired
    ConversationService conversationService;

    /**
     * 建立连接时的处理，利用session通信
     * @param session OnOpen 处理session
     * @param id 发起者id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        // 将局部的session对象赋值给成员session
        this.session = session;
        int userId = Integer.parseInt(id);
        // 将当前用户的 session 加入到 map 里面
        if(webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this.session);
        }else {
            webSocketMap.put(userId, this.session);
        }
    }

    @OnClose
    public void onClose(@PathParam("id") String id) {
        // 关闭时，将用户的 session 删除
        Integer userId = Integer.parseInt(id);
        if(webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
        }
    }

    @OnMessage
    public void onMessage(@PathParam("id") String id, String message) {
        ReceiveMessageVO receiveMessageVO = JSONObject.parseObject(message, ReceiveMessageVO.class);
        if(receiveMessageVO.getMessage() == null || "".equals(receiveMessageVO.getMessage())) {
            return;
        }
        Integer userId = Integer.parseInt(id);
        Integer toId = receiveMessageVO.getToUserId();
        if(webSocketMap.containsKey(toId)) {
            // 在线，则通过 session 的
            try {
                conversationService.sendDialog(userId, toId, receiveMessageVO.getMessage(), receiveMessageVO.getSendTime(), 1);
                ToMessageVO toMessageVO = new ToMessageVO(false, userId, toId, receiveMessageVO.getMessage(),
                        receiveMessageVO.getSendTime());
                webSocketMap.get(toId).getAsyncRemote().sendText(JSONObject.toJSONString(toMessageVO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 不在线，存到数据库，保存为 0
            conversationService.sendDialog(userId, toId, receiveMessageVO.getMessage(), receiveMessageVO.getSendTime(), 0);
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
