package com.qingmeng.service.impl;

import com.qingmeng.dao.ChatFriendRoomDao;
import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.service.ChatFriendRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    /**
     * 按键获取信息
     *
     * @param tagKey 标签键
     * @return {@link ChatFriendRoom }
     * @author qingmeng
     * @createTime: 2023/12/01 09:09:28
     */
    @Override
    public ChatFriendRoom getInfoByKey(String tagKey) {
        return chatFriendRoomDao.getInfoByKey(tagKey);
    }
}
