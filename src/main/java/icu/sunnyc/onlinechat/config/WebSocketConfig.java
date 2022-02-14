package icu.sunnyc.onlinechat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author ：hc
 * @date ：Created in 2022/2/4 21:09
 *
 * EnableWebSocketMessageBroker 用于启动websocket服务器
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个websocket路径，客户端就通过这个路径连上我们的websocket服务器
        // 后面的withSockJS是对于不支持websocket的浏览器的备用选项
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * 对websocket消息发送规则相关配置
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端 向 服务端 发送消息的url前缀
        // 搭配 @MessageMapping("/chat/sendMessage") 使用
        // 以上述为例，客户端向服务端发送消息应该是这样 stompClient.send("/app/chat/sendMessage")
        registry.setApplicationDestinationPrefixes("/app");
        // 服务端 向 客户端 发送消息的url前缀
        // 只有用enableSimpleBroker打开的地址前缀才可以在程序中使用，使用没设置enable的前缀时不会出错，但无法传递消息
        // 搭配 @SendTo("/topic/public") 使用
        // 也就是这里enable了这个 "/topic" 前缀，然后sendTo就可以往以/topic开头的url发消息了，往其他前缀开头的url不能发送
        // 但凡enable一个路径前缀，就会只允许这个前缀发送消息，如果一个也不设置，那就发送消息url没有限制
        registry.enableSimpleBroker("/topic");
    }
}
