package icu.sunnyc.onlinechat.listener;

import icu.sunnyc.onlinechat.entity.ChatMessage;
import icu.sunnyc.onlinechat.enums.MessageType;
import icu.sunnyc.onlinechat.utils.WebSocketConnectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

/**
 * @author ：hc
 * @date ：Created in 2022/2/4 21:57
 * @modified ：
 */
@Component
@Slf4j
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    /**
     * 监听客户端连接事件
     * @param event SessionConnectedEvent
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // 因为在controller的addUser里面已经put过username了，所以这里不用处理了，打印日志即可
        log.info("Received a new web socket connection");
    }

    /**
     * 监听客户端断开连接事件
     * @param event SessionDisconnectEvent
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("username");
        if (username != null) {
            log.info("User Disconnected: " + username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setSender(username);

            // 用户离线，向客户端发送离线消息
            messageTemplate.convertAndSend("/topic/public", chatMessage);
            // 更新在线人数
            messageTemplate.convertAndSend("/topic/onlineNumber", WebSocketConnectUtil.onlineNumberDecrement());
        }
    }


}
