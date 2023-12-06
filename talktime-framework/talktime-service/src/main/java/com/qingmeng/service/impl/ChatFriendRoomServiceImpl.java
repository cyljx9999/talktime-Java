package com.qingmeng.service.impl;

import com.qingmeng.adapt.RoomAdapt;
import com.qingmeng.dao.ChatFriendRoomDao;
import com.qingmeng.dao.ChatRoomDao;
import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.entity.ChatRoom;
import com.qingmeng.service.ChatFriendRoomService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月27日 10:29:00
 */
@Service
public class ChatFriendRoomServiceImpl implements ChatFriendRoomService {
    @Resource
    private ChatFriendRoomDao chatFriendRoomDao;
    @Resource
    private ChatRoomDao chatRoomDao;

    /**
     * 保存聊天好友室
     *
     * @param ids IDS
     * @author qingmeng
     * @createTime: 2023/12/01 16:36:25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveChatFriendRoom(List<Long> ids) {
        AssertUtils.isNotEmpty(ids, "房间创建失败，好友数量不对");
        AssertUtils.equal(ids.size(), 2, "房间创建失败，好友数量不对");
        // 新增抽象好友房间记录
        ChatRoom chatRoom = RoomAdapt.buildDefaultFriendRoom();
        chatRoomDao.save(chatRoom);
        // 新增好友房间记录
        ChatFriendRoom chatFriendRoom = RoomAdapt.buildChatFriendRoom(chatRoom.getId(), ids, CommonUtils.getKeyBySort(ids));
        chatFriendRoomDao.save(chatFriendRoom);
    }
}
