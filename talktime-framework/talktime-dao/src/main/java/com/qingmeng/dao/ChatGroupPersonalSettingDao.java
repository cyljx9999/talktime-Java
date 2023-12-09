package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.chatGroup.AlterGroupPersonalSettingDTO;
import com.qingmeng.entity.ChatGroupPersonalSetting;
import com.qingmeng.mapper.ChatGroupPersonalSettingMapper;
import org.springframework.stereotype.Service;

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
    public void alterPersonSetting(Long userId, AlterGroupPersonalSettingDTO alterGroupPersonalSettingDTO) {
        lambdaUpdate()
                .set(Objects.nonNull(alterGroupPersonalSettingDTO.getDisplayNameStatus()),ChatGroupPersonalSetting::getDisplayNameStatus, alterGroupPersonalSettingDTO.getDisplayNameStatus())
                .set(Objects.nonNull(alterGroupPersonalSettingDTO.getRemindStatus()),ChatGroupPersonalSetting::getRemindStatus, alterGroupPersonalSettingDTO.getRemindStatus())
                .set(Objects.nonNull(alterGroupPersonalSettingDTO.getTopStatus()),ChatGroupPersonalSetting::getTopStatus, alterGroupPersonalSettingDTO.getTopStatus())
                .eq(ChatGroupPersonalSetting::getUserId, userId)
                .eq(ChatGroupPersonalSetting::getGroupRoomId, alterGroupPersonalSettingDTO.getGroupRoomId())
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
}
