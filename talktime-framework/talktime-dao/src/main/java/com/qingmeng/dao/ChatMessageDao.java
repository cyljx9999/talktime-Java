package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.enums.chat.MessageStatusEnum;
import com.qingmeng.mapper.ChatMessageMapper;
import com.qingmeng.utils.CursorUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

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

    public CursorPageBaseVO<ChatMessage> getCursorPage(Long roomId, CursorPageBaseDTO request, Long lastMsgId) {
        return CursorUtils.getCursorPageByMysql(this, request, wrapper -> {
            wrapper.eq(ChatMessage::getRoomId, roomId);
            wrapper.eq(ChatMessage::getStatus, MessageStatusEnum.NORMAL.getCode());
            wrapper.le(Objects.nonNull(lastMsgId), ChatMessage::getId, lastMsgId);
        }, ChatMessage::getId);
    }

    /**
     * 获取 未读取计数
     *
     * @param roomId   房间 ID
     * @param readTime 读取时间
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/12 15:24:50
     */
    public Long getUnReadCount(Long roomId, Date readTime) {
        return lambdaQuery()
                .eq(ChatMessage::getRoomId, roomId)
                .gt(Objects.nonNull(readTime), ChatMessage::getCreateTime, readTime)
                .count();
    }
}
