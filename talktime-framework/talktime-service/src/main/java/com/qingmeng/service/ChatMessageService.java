package com.qingmeng.service;

import com.qingmeng.dto.chat.ChatMessageDTO;

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
}
