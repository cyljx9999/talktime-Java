package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.mapper.SysUserFriendSettingMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:17:00
 */
@Service
public class SysUserFriendSettingDao extends ServiceImpl<SysUserFriendSettingMapper, SysUserFriendSetting>{

    /**
     * 通过key标识和用户 ID 获取设置
     *
     * @param tagKey 标签键
     * @param userId 用户 ID
     * @return {@link SysUserFriendSetting }
     * @author qingmeng
     * @createTime: 2023/11/29 14:44:31
     */
    public SysUserFriendSetting getSettingByKeyAndUserId(String tagKey, Long userId) {
        return lambdaQuery()
                .eq(SysUserFriendSetting::getTagKey,tagKey)
                .eq(SysUserFriendSetting::getUserId,userId)
                .one();
    }

    /**
     * 更改设置
     *
     * @param userFriendSettingDTO 用户好友设置 DTO
     * @author qingmeng
     * @createTime: 2023/11/29 15:21:35
     */
    public void alterSetting(UserFriendSettingDTO userFriendSettingDTO) {
        lambdaUpdate()
                .eq(StrUtil.isNotBlank(userFriendSettingDTO.getNickName()),SysUserFriendSetting::getNickName,userFriendSettingDTO.getNickName())
                .eq(Objects.nonNull(userFriendSettingDTO.getFriendStatus()),SysUserFriendSetting::getFriendStatus,userFriendSettingDTO.getFriendStatus())
                .eq(Objects.nonNull(userFriendSettingDTO.getTopStatus()),SysUserFriendSetting::getTopStatus,userFriendSettingDTO.getTopStatus())
                .update(new SysUserFriendSetting());
    }
}
