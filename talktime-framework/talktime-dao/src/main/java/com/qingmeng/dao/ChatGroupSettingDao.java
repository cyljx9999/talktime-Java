package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.chatGroup.AlterGroupSettingDTO;
import com.qingmeng.entity.ChatGroupSetting;
import com.qingmeng.mapper.ChatGroupSettingMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 群聊设置 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatGroupSettingDao extends ServiceImpl<ChatGroupSettingMapper, ChatGroupSetting>{

    /**
     * 按组房间 ID 获取设置
     *
     * @param groupRoomId 组会议室 ID
     * @return {@link ChatGroupSetting }
     * @author qingmeng
     * @createTime: 2023/12/07 09:17:50
     */
    public ChatGroupSetting getSettingByGroupRoomId(Long groupRoomId) {
        return lambdaQuery().eq(ChatGroupSetting::getGroupRoomId,groupRoomId).one();
    }

    /**
     * 更新设置
     *
     * @param alterGroupSettingDTO 更改组设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 10:34:06
     */
    public void updateSetting(AlterGroupSettingDTO alterGroupSettingDTO) {
        lambdaUpdate()
                .set(StrUtil.isNotBlank(alterGroupSettingDTO.getGroupRoomAvatar()),ChatGroupSetting::getGroupRoomAvatar,alterGroupSettingDTO.getGroupRoomAvatar())
                .set(StrUtil.isNotBlank(alterGroupSettingDTO.getGroupRoomName()),ChatGroupSetting::getGroupRoomName,alterGroupSettingDTO.getGroupRoomName())
                .set(Objects.nonNull(alterGroupSettingDTO.getInvitationConfirmation()),ChatGroupSetting::getInvitationConfirmation,alterGroupSettingDTO.getInvitationConfirmation())
                .eq(ChatGroupSetting::getGroupRoomId,alterGroupSettingDTO.getGroupRoomId())
                .update(new ChatGroupSetting());

    }
}
