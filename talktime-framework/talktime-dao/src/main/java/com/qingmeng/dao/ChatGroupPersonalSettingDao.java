package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.chatGroup.AlterGroupPersonalSettingDTO;
import com.qingmeng.entity.ChatGroupPersonalSetting;
import com.qingmeng.mapper.ChatGroupPersonalSettingMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 群聊个人设置 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatGroupPersonalSettingDao extends ServiceImpl<ChatGroupPersonalSettingMapper, ChatGroupPersonalSetting>{

    /**
     * 更改人员设置
     *
     * @param userId                       用户 ID
     * @param alterGroupPersonalSettingDTO 更改组个人设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/09 13:40:43
     */
    public void alterPersonSetting(Long userId,Long groupRoomId, AlterGroupPersonalSettingDTO alterGroupPersonalSettingDTO) {
        lambdaUpdate()
                .set(Objects.nonNull(alterGroupPersonalSettingDTO.getDisplayNameStatus()),ChatGroupPersonalSetting::getDisplayNameStatus, alterGroupPersonalSettingDTO.getDisplayNameStatus())
                .set(Objects.nonNull(alterGroupPersonalSettingDTO.getRemindStatus()),ChatGroupPersonalSetting::getRemindStatus, alterGroupPersonalSettingDTO.getRemindStatus())
                .set(Objects.nonNull(alterGroupPersonalSettingDTO.getTopStatus()),ChatGroupPersonalSetting::getTopStatus, alterGroupPersonalSettingDTO.getTopStatus())
                .eq(ChatGroupPersonalSetting::getUserId, userId)
                .eq(ChatGroupPersonalSetting::getGroupRoomId, groupRoomId)
                .update(new ChatGroupPersonalSetting());
    }

    /**
     * 通过用户ID获取设置
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @return {@link ChatGroupPersonalSetting }
     * @author qingmeng
     * @createTime: 2023/12/09 14:04:25
     */
    public ChatGroupPersonalSetting getSettingByUserId(Long userId, Long groupRoomId) {
        return lambdaQuery()
                .eq(ChatGroupPersonalSetting::getUserId,userId)
                .eq(ChatGroupPersonalSetting::getGroupRoomId,groupRoomId)
                .one();
    }

    /**
     * 按组会议室 ID 列出
     *
     * @param groupRoomIds 组聊天室 ID
     * @return {@link List }<{@link ChatGroupPersonalSetting }>
     * @author qingmeng
     * @createTime: 2023/12/10 10:20:15
     */
    public List<ChatGroupPersonalSetting> listByGroupRoomIds(List<Long> groupRoomIds) {
        return lambdaQuery().in(ChatGroupPersonalSetting::getGroupRoomId,groupRoomIds).list();
    }

    /**
     * 按组房间 ID 和用户 ID 获取
     *
     * @param groupRoomId 组会议室 ID
     * @param userId      用户 ID
     * @return {@link ChatGroupPersonalSetting }
     * @author qingmeng
     * @createTime: 2023/12/10 11:37:38
     */
    public ChatGroupPersonalSetting getByGroupRoomIdAndUserId(String groupRoomId, String userId) {
        return lambdaQuery()
                .eq(ChatGroupPersonalSetting::getGroupRoomId,Long.valueOf(groupRoomId))
                .eq(ChatGroupPersonalSetting::getUserId,Long.valueOf(userId))
                .one();
    }

    /**
     * 删除设置
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/10 11:43:14
     */
    public void removeSetting(Long userId, Long groupRoomId) {
        LambdaQueryWrapper<ChatGroupPersonalSetting> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatGroupPersonalSetting::getUserId, userId);
        wrapper.eq(ChatGroupPersonalSetting::getGroupRoomId, groupRoomId);
        remove(wrapper);
    }
}
