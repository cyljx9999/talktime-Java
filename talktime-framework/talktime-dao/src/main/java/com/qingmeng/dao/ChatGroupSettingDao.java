package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.chatGroup.AlterGroupSettingDTO;
import com.qingmeng.entity.ChatGroupSetting;
import com.qingmeng.mapper.ChatGroupSettingMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .set(StrUtil.isNotBlank(alterGroupSettingDTO.getGroupNotice()),ChatGroupSetting::getGroupNotice,alterGroupSettingDTO.getGroupNotice())
                .set(Objects.nonNull(alterGroupSettingDTO.getInvitationConfirmation()),ChatGroupSetting::getInvitationConfirmation,alterGroupSettingDTO.getInvitationConfirmation())
                .eq(ChatGroupSetting::getGroupRoomId,alterGroupSettingDTO.getGroupRoomId())
                .update(new ChatGroupSetting());

    }

    /**
     * 按组会议室 ID 列出
     *
     * @param groupRoomIds 组聊天室 ID
     * @return {@link List }<{@link ChatGroupSetting }>
     * @author qingmeng
     * @createTime: 2023/12/10 10:21:31
     */
    public List<ChatGroupSetting> listByGroupRoomIds(List<Long> groupRoomIds) {
        return lambdaQuery().in(ChatGroupSetting::getGroupRoomId,groupRoomIds).list();
    }
}
