package com.qingmeng.service.impl;

import com.qingmeng.adapt.RoomAdapt;
import com.qingmeng.dao.ChatRoomDao;
import com.qingmeng.entity.ChatRoom;
import com.qingmeng.service.ChatRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月27日 10:27:00
 */
@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Resource
    private ChatRoomDao chatRoomDao;

    /**
     * 保存朋友房间
     *
     * @author qingmeng
     * @createTime: 2023/12/01 16:33:11
     */
    @Override
    public void saveFriendRoom() {
        // 新增抽象好友房间记录
        ChatRoom chatRoom = RoomAdapt.buildDefaultFriendRoom();
        chatRoomDao.save(chatRoom);
    }
}
