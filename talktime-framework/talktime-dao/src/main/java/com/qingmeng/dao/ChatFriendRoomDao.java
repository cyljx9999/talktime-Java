package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.mapper.ChatFriendRoomMapper;
import org.springframework.stereotype.Service;

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
}
