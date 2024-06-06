package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatRoom;
import com.qingmeng.mapper.ChatRoomMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 聊天房间 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatRoomDao extends ServiceImpl<ChatRoomMapper, ChatRoom>{
    /**
     * 刷新消息时间
     *
     * @param roomId  房间 ID
     * @param msgId   味精 ID
     * @param msgTime 味精时间
     * @author qingmeng
     * @createTime: 2024/06/07 00:07:18
     */
    public void refreshActiveTime(Long roomId, Long msgId, Date msgTime) {
        lambdaUpdate()
                .eq(ChatRoom::getId, roomId)
                .set(ChatRoom::getLastMsgId, msgId)
                .set(ChatRoom::getActiveTime, msgTime)
                .update();
    }
}
