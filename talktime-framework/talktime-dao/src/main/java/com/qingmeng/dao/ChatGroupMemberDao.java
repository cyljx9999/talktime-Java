package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatGroupMember;
import com.qingmeng.mapper.ChatGroupMemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 群聊成员表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatGroupMemberDao extends ServiceImpl<ChatGroupMemberMapper, ChatGroupMember> {

    /**
     * 按组房间 ID 获取成员计数
     *
     * @param groupRoomId 组会议室 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2023/12/07 08:49:21
     */
    public Long getMemberCountByGroupRoomId(Long groupRoomId) {
        return lambdaQuery().eq(ChatGroupMember::getGroupRoomId, groupRoomId).count();
    }

    /**
     * 通过用户 ID 和组房间 ID 获取
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @return {@link ChatGroupMember }
     * @author qingmeng
     * @createTime: 2023/12/08 08:43:21
     */
    public ChatGroupMember getByUserIdAndGroupRoomId(Long userId, Long groupRoomId) {
        return lambdaQuery()
                .eq(ChatGroupMember::getUserId, userId)
                .eq(ChatGroupMember::getGroupRoomId, groupRoomId)
                .one();
    }

    /**
     * 删除成员
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:31:39
     */
    public void removeMember(Long userId, Long groupRoomId) {
        LambdaQueryWrapper<ChatGroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatGroupMember::getUserId, userId);
        wrapper.eq(ChatGroupMember::getGroupRoomId, groupRoomId);
        remove(wrapper);
    }

    /**
     * 获取群成员列表
     *
     * @param groupRoomId 组会议室 ID
     * @return {@link List }<{@link ChatGroupMember }>
     * @author qingmeng
     * @createTime: 2023/12/09 14:08:58
     */
    public List<ChatGroupMember> getGroupMemberList(Long groupRoomId) {
        return lambdaQuery().eq(ChatGroupMember::getGroupRoomId,groupRoomId).list();
    }
}
