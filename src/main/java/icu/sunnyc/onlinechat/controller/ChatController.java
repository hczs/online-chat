package icu.sunnyc.onlinechat.controller;

import icu.sunnyc.onlinechat.entity.ChatMessage;
import icu.sunnyc.onlinechat.utils.WebSocketConnectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.util.Objects;

/**
 * @author ：hc
 * @date ：Created in 2022/2/4 21:40
 */
@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messageTemplate;


    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // SendTo 必须得和 MessageMapping 搭配使用
        // 客户端向MessageMapping的url发送消息时，会把这个方法的返回值转发给所有连接到websocket的用户
        return chatMessage;
    }

    @MessageMapping("/chat/addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // 添加 username 到 web socket session
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
        // 更新在线人数
        messageTemplate.convertAndSend("/topic/onlineNumber", WebSocketConnectUtil.onlineNumberIncrement());
        log.info("User connected: " + chatMessage.getSender());
        log.info("Number of people currently online: " + WebSocketConnectUtil.getOnlineNumber());
        return chatMessage;
    }

}
