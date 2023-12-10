package com.qingmeng.config.cache;

import com.qingmeng.dao.ChatGroupMemberDao;
import com.qingmeng.dao.ChatGroupRoomDao;
import com.qingmeng.entity.ChatGroupMember;
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
 * @Description
 * @createTime 2023年12月10日 10:05:00
 */
@Component
public class ChatGroupMemberCache {
    @Resource
    private ChatGroupMemberDao chatGroupMemberDao;
    @Resource
    private ChatGroupRoomDao chatGroupRoomDao;

    /**
     * 获取成员用户 ID 列表
     *
     * @param roomId 房间 ID
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/12/10 10:11:27
     */
    @Cacheable(cacheNames = "member", key = "'groupMember:'+#roomId")
    public List<Long> getMemberUserIdList(Long roomId) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomDao.getByRoomId(roomId);
        if (Objects.isNull(chatGroupRoom)) {
            return new ArrayList<>();
        }
        return chatGroupMemberDao.getGroupMemberList(chatGroupRoom.getId()).stream().map(ChatGroupMember::getUserId).collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "member", key = "'groupMember:'+#roomId")
    public List<Long> evictMemberUidList(Long roomId) {
        return null;
    }

}
