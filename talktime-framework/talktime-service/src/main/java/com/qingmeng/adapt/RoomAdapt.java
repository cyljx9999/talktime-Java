package com.qingmeng.adapt;

import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.entity.ChatRoom;
import com.qingmeng.enums.chat.RoomStatusEnum;
import com.qingmeng.enums.chat.RoomTypeEnum;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 房间适配器
 * @createTime 2023年11月28日 17:49:00
 */
public class RoomAdapt {

    /**
     * 建立抽象的单聊房间
     *
     * @return {@link ChatRoom }
     * @author qingmeng
     * @createTime: 2023/11/28 17:55:18
     */
    public static ChatRoom buildDefaultFriendRoom(){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomType(RoomTypeEnum.FRIEND.getCode());
        return chatRoom;
    }

    /**
     * 建立抽象的群聊房间
     *
     * @return {@link ChatRoom }
     * @author qingmeng
     * @createTime: 2023/11/28 17:55:18
     */
    public static ChatRoom buildDefaultGroupRoom(){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomType(RoomTypeEnum.GROUP.getCode());
        return chatRoom;
    }


    /**
     * 建立聊天好友室
     *
     * @param roomId 房间 ID
     * @param ids    IDS
     * @param key    钥匙
     * @return {@link ChatFriendRoom }
     * @author qingmeng
     * @createTime: 2023/12/01 16:48:10
     */
    public static ChatFriendRoom buildChatFriendRoom(Long roomId, List<Long> ids, String key){
        ChatFriendRoom chatFriendRoom = new ChatFriendRoom();
        chatFriendRoom.setRoomId(roomId);
        chatFriendRoom.setUserId(ids.get(0));
        chatFriendRoom.setOtherUserId(ids.get(1));
        chatFriendRoom.setRoomKey(key);
        chatFriendRoom.setRoomStatus(RoomStatusEnum.NORMAL.getCode());
        return chatFriendRoom;
    }

}
