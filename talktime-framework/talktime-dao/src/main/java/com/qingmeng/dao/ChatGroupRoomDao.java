package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatGroupRoom;
import com.qingmeng.mapper.ChatGroupRoomMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 群聊表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatGroupRoomDao extends ServiceImpl<ChatGroupRoomMapper, ChatGroupRoom>{
    /**
     * 按房间 ID 列出
     *
     * @param roomIds 房间 ID
     * @return {@link List }<{@link ChatGroupRoom }>
     * @author qingmeng
     * @createTime: 2023/12/10 10:00:28
     */
    public List<ChatGroupRoom> listByRoomIds(List<Long> roomIds) {
        return lambdaQuery().in(ChatGroupRoom::getRoomId, roomIds).list();
    }

    /**
     * 通过房间 ID 获取
     *
     * @param roomId 房间 ID
     * @return {@link ChatGroupRoom }
     * @author qingmeng
     * @createTime: 2023/12/10 10:09:16
     */
    public ChatGroupRoom getByRoomId(Long roomId) {
        return lambdaQuery().eq(ChatGroupRoom::getRoomId, roomId).one();
    }
}
