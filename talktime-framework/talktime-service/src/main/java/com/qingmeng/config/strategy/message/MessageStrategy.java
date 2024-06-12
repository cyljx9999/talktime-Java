package com.qingmeng.config.strategy.message;

import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.ChatMessage;

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
     * @return {@link ChatMessage }
     * @author qingmeng
     * @createTime: 2024/06/06 22:59:04
     */
    ChatMessage saveMessage(ChatMessageDTO chatMessageDTO,Long userId);

    /**
     * 检查消息
     *
     * @param messageDTO 消息 DTO
     * @param userId     用户 ID
     * @author qingmeng
     * @createTime: 2024/06/04 21:56:34
     */
    void checkMsg(ChatMessageDTO messageDTO,Long userId);

    /**
     * 展示消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/06 10:43:34
     */
    Object showMsg(ChatMessage msg);

    /**
     *  被回复时——展示的消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:24
     */
    Object showReplyMsg(ChatMessage msg);


    /**
     * 会话列表——展示的消息
     *
     * @param msg 消息
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:14
     */
    String showSessionMsg(ChatMessage msg);

    /**
     * 保存额外消息
     *
     * @param msg        消息
     * @param messageDTO 消息 DTO
     * @author qingmeng
     * @createTime: 2024/06/04 21:59:46
     */
    void saveExtraMessage(ChatMessage msg, ChatMessageDTO messageDTO);
}
