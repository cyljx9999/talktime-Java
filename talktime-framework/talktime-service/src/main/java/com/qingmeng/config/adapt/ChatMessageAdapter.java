package com.qingmeng.config.adapt;

import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.ChatMessage;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息适配类
 * @createTime 2024年06月04日 22:11:00
 */
public class ChatMessageAdapter {

    public static ChatMessage buildChatMessageSave(ChatMessageDTO chatMessageDTO, Long userId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(chatMessageDTO.getRoomId());
        chatMessage.setFromUserId(userId);
        chatMessage.setStatus(0);
        chatMessage.setMessageType(chatMessageDTO.getMsgType());
        return chatMessage;
    }

}
