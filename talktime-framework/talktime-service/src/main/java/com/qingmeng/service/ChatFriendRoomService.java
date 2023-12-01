package com.qingmeng.service;

import java.util.List;

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
     * 保存聊天好友室
     *
     * @param ids IDS
     * @author qingmeng
     * @createTime: 2023/12/01 16:36:25
     */
    void saveChatFriendRoom(List<Long> ids);
}
