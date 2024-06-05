package com.qingmeng.config.strategy.message;

import com.qingmeng.dto.chat.ChatMessageDTO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 信息通用接口
 * @createTime 2024年06月04日 21:09:00
 */
public interface MessageStrategy {
    /**
     * 保存消息
     *
     * @param chatMessageDTO 聊天消息 DTO
     * @param userId         用户 ID
     * @author qingmeng
     * @createTime: 2024/06/04 22:04:39
     */
    void saveMessage(ChatMessageDTO chatMessageDTO,Long userId);
}
