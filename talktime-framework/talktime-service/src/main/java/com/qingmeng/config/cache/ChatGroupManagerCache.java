package com.qingmeng.config.cache;

import com.qingmeng.dao.ChatGroupManagerDao;
import com.qingmeng.entity.ChatGroupManager;
import com.qingmeng.entity.ChatGroupRoom;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 抽象房间缓存
 * @createTime 2023年12月10日 09:22:00
 */
@Component
public class ChatGroupManagerCache {
    @Resource
    private ChatGroupManagerDao chatGroupManagerDao;
    @Resource
    private ChatGroupRoomCache chatGroupRoomCache;

    /**
     * 获取管理成员用户 ID 列表
     *
     * @param roomId 房间 ID
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/12/10 10:11:27
     */
    @Cacheable(cacheNames = "manager", key = "'groupManager:'+#roomId")
    public List<Long> getManagerIdList(Long roomId) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        if (Objects.isNull(chatGroupRoom)) {
            return new ArrayList<>();
        }
        return chatGroupManagerDao.listByRoomIds(chatGroupRoom.getId()).stream().map(ChatGroupManager::getUserId).collect(Collectors.toList());
    }

    /**
     * 获取管理全部列表
     *
     * @param roomId 房间 ID
     * @return {@link List }<{@link ChatGroupManager }>
     * @author qingmeng
     * @createTime: 2023/12/11 10:59:53
     */
    @Cacheable(cacheNames = "managerAll", key = "'groupManagerAll:'+#roomId")
    public List<ChatGroupManager> getManagerAllList(Long roomId) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        if (Objects.isNull(chatGroupRoom)) {
            return new ArrayList<>();
        }
        return chatGroupManagerDao.listByRoomIds(chatGroupRoom.getId());
    }

    @CacheEvict(cacheNames = "managerAll", key = "'groupManagerAll:'+#roomId")
    public List<Long> evictManagerAllList(Long roomId) {
        return null;
    }

    @CacheEvict(cacheNames = "member", key = "'groupManager:'+#roomId")
    public List<Long> evictManagerIdList(Long roomId) {
        return null;
    }


    /**
     * 获取成员UID列表
     *
     * @param roomId 房间 ID
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2024/06/07 00:15:09
     */
    public List<Long> getMemberUidList(Long roomId) {
        ChatGroupRoom roomGroup = chatGroupRoomCache.get(roomId);
        if (Objects.isNull(roomGroup)) {
            return null;
        }
        return chatGroupManagerDao.getMemberUidList(roomGroup.getId());
    }
}
