package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 信息
 * @createTime 2024年06月04日 22:05:00
 */
@Service
public class ChatMessageDao extends ServiceImpl<ChatMessageMapper, ChatMessage> {
    /**
     * 获取消息间隔条数
     *
     * @param roomId 房间 ID
     * @param fromId 从 id
     * @param toId   更改为 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/06 11:24:50
     */
    public Long getGapCount(Long roomId, Long fromId, Long toId) {
        return lambdaQuery()
                .eq(ChatMessage::getRoomId, roomId)
                .gt(ChatMessage::getId, fromId)
                .le(ChatMessage::getId, toId)
                .count();
    }
}
