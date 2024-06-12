package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.entity.ChatSession;
import com.qingmeng.mapper.ChatSessionMapper;
import com.qingmeng.utils.CursorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月09日 19:17:00
 */
@Service
public class ChatSessionDao extends ServiceImpl<ChatSessionMapper, ChatSession> {
    /**
     * 获取总数
     *
     * @param roomId 房间 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/09 19:19:35
     */
    public Long getTotalCount(Long roomId) {
        return lambdaQuery()
                .eq(ChatSession::getRoomId, roomId)
                .count();
    }

    /**
     * 获取已读数
     *
     * @param chatMessage 聊天消息
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/09 19:20:48
     */
    public Long getReadCount(ChatMessage chatMessage) {
        return lambdaQuery()
                .eq(ChatSession::getRoomId, chatMessage.getRoomId())
                .ne(ChatSession::getUserId, chatMessage.getFromUserId())
                .ge(ChatSession::getReadTime, chatMessage.getCreateTime())
                .count();
    }

    /**
     * 按房间 ID 获取
     *
     * @param userId 用户 ID
     * @param roomId 房间 ID
     * @return {@link ChatSession }
     * @author qingmeng
     * @createTime: 2024/06/09 19:26:23
     */
    public ChatSession getByRoomId(Long userId, Long roomId) {
        return lambdaQuery()
                .eq(ChatSession::getUserId, userId)
                .eq(ChatSession::getRoomId, roomId)
                .one();
    }

    /**
     * 获取已读页面
     *
     * @param message           消息
     * @param cursorPageBaseDTO 基于光标页 DTO
     * @return {@link CursorPageBaseVO }<{@link ChatSession }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:57:31
     */
    public CursorPageBaseVO<ChatSession> getReadPage(ChatMessage message, CursorPageBaseDTO cursorPageBaseDTO) {
        return CursorUtils.getCursorPageByMysql(this, cursorPageBaseDTO, wrapper -> {
            wrapper.eq(ChatSession::getRoomId, message.getRoomId());
            wrapper.ne(ChatSession::getUserId, message.getFromUserId());
            wrapper.ge(ChatSession::getReadTime, message.getCreateTime());
        }, ChatSession::getReadTime);
    }

    /**
     * 获取 未读页面
     *
     * @param message           消息
     * @param cursorPageBaseDTO 基于光标页 DTO
     * @return {@link CursorPageBaseVO }<{@link ChatSession }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:57:28
     */
    public CursorPageBaseVO<ChatSession> getUnReadPage(ChatMessage message, CursorPageBaseDTO cursorPageBaseDTO) {
        return CursorUtils.getCursorPageByMysql(this, cursorPageBaseDTO, wrapper -> {
            wrapper.eq(ChatSession::getRoomId, message.getRoomId());
            wrapper.ne(ChatSession::getUserId, message.getFromUserId());
            wrapper.lt(ChatSession::getReadTime, message.getCreateTime());
        }, ChatSession::getReadTime);
    }

    /**
     * 按用户 ID 和房间 ID 获取
     *
     * @param receiveUid 接收 UID
     * @param roomId     房间 ID
     * @return {@link ChatSession }
     * @author qingmeng
     * @createTime: 2024/06/09 20:11:38
     */
    public ChatSession getByUserIdAndRoomId(Long receiveUid, Long roomId) {
        return lambdaQuery()
                .eq(ChatSession::getUserId, receiveUid)
                .eq(ChatSession::getRoomId, roomId)
                .one();
    }

    public CursorPageBaseVO<ChatSession> getSessionPage(Long userId, CursorPageBaseDTO cursorPageBaseDTO) {
        return CursorUtils.getCursorPageByMysql(this, cursorPageBaseDTO, wrapper -> {
            wrapper.eq(ChatSession::getUserId, userId);
        }, ChatSession::getActiveTime);
    }

    /**
     * 按房间 ID 获取
     *
     * @param roomIds 房间 ID
     * @param userId  用户 ID
     * @return {@link List }<{@link ChatSession }>
     * @author qingmeng
     * @createTime: 2024/06/12 15:25:44
     */
    public List<ChatSession> getByRoomIds(List<Long> roomIds, Long userId) {
        return lambdaQuery()
                .in(ChatSession::getRoomId, roomIds)
                .eq(ChatSession::getUserId, userId)
                .list();
    }
}
