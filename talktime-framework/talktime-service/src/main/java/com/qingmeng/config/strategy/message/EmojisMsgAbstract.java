package com.qingmeng.config.strategy.message;

import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.utils.AssertUtils;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 表情包信息类
 * @createTime 2024年06月05日 21:39:00
 */
@Component
public class EmojisMsgAbstract extends AbstractMessageStrategy{


    /**
     * 检查消息
     *
     * @param messageDTO 消息 DTO
     * @param userId     用户 ID
     * @author qingmeng
     * @createTime: 2024/06/04 21:56:34
     */
    @Override
    protected void checkMsg(ChatMessageDTO messageDTO, Long userId) {
        AssertUtils.validateEntity(messageDTO,false);
    }

    /**
     * 展示消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:30
     */
    @Override
    public Object showMsg(ChatMessage msg) {
        return null;
    }

    /**
     * 被回复时——展示的消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:24
     */
    @Override
    public Object showReplyMsg(ChatMessage msg) {
        return null;
    }

    /**
     * 会话列表——展示的消息
     *
     * @param msg 消息
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:14
     */
    @Override
    public String showContactMsg(ChatMessage msg) {
        return null;
    }

    /**
     * 保存额外消息
     *
     * @param msg        消息
     * @param messageDTO 消息 DTO
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/04 21:59:46
     */
    @Override
    public String saveExtraMessage(ChatMessage msg, ChatMessageDTO messageDTO) {
        return null;
    }
}
