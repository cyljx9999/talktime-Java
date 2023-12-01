package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.enums.system.LogicDeleteEnum;
import com.qingmeng.mapper.SysUserFriendSettingMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:17:00
 */
@Service
public class SysUserFriendSettingDao extends ServiceImpl<SysUserFriendSettingMapper, SysUserFriendSetting> {

    /**
     * 更改设置
     *
     * @param userFriendSettingDTO 用户好友设置 DTO
     * @author qingmeng
     * @createTime: 2023/11/29 15:21:35
     */
    public void alterSetting(UserFriendSettingDTO userFriendSettingDTO) {
        lambdaUpdate()
                .set(StrUtil.isNotBlank(userFriendSettingDTO.getNickName()), SysUserFriendSetting::getNickName, userFriendSettingDTO.getNickName())
                .set(Objects.nonNull(userFriendSettingDTO.getFriendStatus()), SysUserFriendSetting::getFriendStatus, userFriendSettingDTO.getFriendStatus())
                .set(Objects.nonNull(userFriendSettingDTO.getTopStatus()), SysUserFriendSetting::getTopStatus, userFriendSettingDTO.getTopStatus())
                .set(Objects.nonNull(userFriendSettingDTO.getRemindStatus()), SysUserFriendSetting::getRemindStatus, userFriendSettingDTO.getRemindStatus())
                .update(new SysUserFriendSetting());
    }

    /**
     * 按标签键删除
     *
     * @param tagKey 标签键
     * @author qingmeng
     * @createTime: 2023/12/01 09:24:26
     */
    public void removeByTagKey(String tagKey) {
        lambdaUpdate()
                .eq(SysUserFriendSetting::getTagKey, tagKey)
                .set(SysUserFriendSetting::getIsDeleted, LogicDeleteEnum.IS_DELETE.getCode())
                .update(new SysUserFriendSetting());
    }
}
