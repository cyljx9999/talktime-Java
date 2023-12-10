package com.qingmeng.config.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.ChatGroupRoomDao;
import com.qingmeng.entity.ChatGroupRoom;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 抽象房间缓存
 * @createTime 2023年12月10日 09:22:00
 */
@Component
public class ChatGroupRoomCache extends AbstractRedisStringCache<Long, ChatGroupRoom> {
    @Resource
    private ChatGroupRoomDao chatGroupRoomDao;

    /**
     * 根据输入对象获取缓存的键。
     *
     * @param roomId 输入对象
     * @return 缓存键
     */
    @Override
    protected String getKey(Long roomId) {
        return RedisConstant.CHAT_GROUP_ROOM_KEY + roomId;
    }

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return 过期时间（秒）
     */
    @Override
    protected Long getExpireSeconds() {
        return RedisConstant.CHAT_GROUP_ROOM_EXPIRE * SystemConstant.DAY;
    }

    /**
     * 批量加载缓存数据。
     *
     * @param roomIds 批量请求列表
     * @return 加载的缓存数据映射
     */
    @Override
    protected Map<Long, ChatGroupRoom> load(List<Long> roomIds) {
        List<ChatGroupRoom> list = chatGroupRoomDao.listByRoomIds(roomIds);
        return list.stream().collect(Collectors.toMap(ChatGroupRoom::getRoomId, Function.identity()));
    }


}
