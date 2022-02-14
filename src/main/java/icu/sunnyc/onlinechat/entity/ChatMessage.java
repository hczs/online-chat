package icu.sunnyc.onlinechat.entity;

import icu.sunnyc.onlinechat.enums.MessageType;
import lombok.Data;

/**
 * @author ：hc
 * @date ：Created in 2022/2/4 21:19
 *
 * 聊天信息 - 实体类
 */
@Data
public class ChatMessage {

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送者
     */
    private String sender;
}
