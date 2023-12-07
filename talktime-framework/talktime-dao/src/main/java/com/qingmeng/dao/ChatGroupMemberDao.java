package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatGroupMember;
import com.qingmeng.mapper.ChatGroupMemberMapper;
import org.springframework.stereotype.Service;

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
}
