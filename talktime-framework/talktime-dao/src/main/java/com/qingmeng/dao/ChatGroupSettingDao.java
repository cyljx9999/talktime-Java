package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatGroupSetting;
import com.qingmeng.mapper.ChatGroupSettingMapper;
import org.springframework.stereotype.Service;

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
}
