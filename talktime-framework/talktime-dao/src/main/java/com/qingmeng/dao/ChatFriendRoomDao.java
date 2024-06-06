package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.mapper.ChatFriendRoomMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 单聊表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatFriendRoomDao extends ServiceImpl<ChatFriendRoomMapper, ChatFriendRoom> {

    /**
     * 按键获取信息
     *
     * @param tagKey 标签键
     * @return {@link ChatFriendRoom }
     * @author qingmeng
     * @createTime: 2023/12/01 09:10:01
     */
    public ChatFriendRoom getInfoByKey(String tagKey) {
        return lambdaQuery().eq(ChatFriendRoom::getRoomKey,tagKey).one();
    }


    /**
     * 按房间 ID 列出
     *
     * @param roomIds 房间 ID
     * @return {@link List }<{@link ChatFriendRoom }>
     * @author qingmeng
     * @createTime: 2023/12/10 09:54:24
     */
    public List<ChatFriendRoom> listByRoomIds(List<Long> roomIds) {
        return lambdaQuery().in(ChatFriendRoom::getRoomId,roomIds).list();
    }

    /**
     * 按房间 ID 获取
     *
     * @param roomId 房间 ID
     * @return {@link ChatFriendRoom }
     * @author qingmeng
     * @createTime: 2024/06/06 22:45:41
     */
    public ChatFriendRoom getByRoomId(Long roomId) {
        return lambdaQuery()
                .eq(ChatFriendRoom::getRoomId, roomId)
                .one();
    }
}
