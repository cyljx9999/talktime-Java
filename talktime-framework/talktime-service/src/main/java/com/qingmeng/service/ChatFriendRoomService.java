package com.qingmeng.service;

import com.qingmeng.entity.ChatFriendRoom;

/**
 * <p>
 * 单聊表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
public interface ChatFriendRoomService{

    /**
     * 按键获取信息
     *
     * @param tagKey 标签键
     * @return {@link ChatFriendRoom }
     * @author qingmeng
     * @createTime: 2023/12/01 09:09:28
     */
    ChatFriendRoom getInfoByKey(String tagKey);
}
