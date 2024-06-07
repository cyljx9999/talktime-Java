package com.qingmeng.service;

import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.vo.chat.ChatMessageVO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月04日 22:07:00
 */
public interface ChatMessageService {
    /**
     * 发送消息
     *
     * @param chatMessageDTO 聊天消息 DTO
     * @param userId         用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/06 22:40:57
     */
    Long sendMsg(ChatMessageDTO chatMessageDTO, Long userId);

    /**
     * 获取聊天消息 VO
     *
     * @param msgId      消息 ID
     * @param receiveUid 接收 UID
     * @return {@link ChatMessageVO }
     * @author qingmeng
     * @createTime: 2024/06/07 22:53:52
     */
    ChatMessageVO getChatMessageVO(Long msgId, Long receiveUid);

    /**
     * 获取聊天消息 VO
     *
     * @param chatMessage 聊天消息
     * @param receiveUid  接收 UID
     * @return {@link ChatMessageVO }
     * @author qingmeng
     * @createTime: 2024/06/07 22:54:47
     */
    ChatMessageVO getChatMessageVO(ChatMessage chatMessage, Long receiveUid);
}
