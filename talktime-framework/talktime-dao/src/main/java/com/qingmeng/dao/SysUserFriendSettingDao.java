package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.enums.system.LogicDeleteEnum;
import com.qingmeng.mapper.SysUserFriendSettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    /**
     * 按自定义保存或更新批处理
     *
     * @param friendSettingList 好友设置列表
     * @author qingmeng
     * @createTime: 2023/12/01 16:14:24
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateBatchByCustom(List<SysUserFriendSetting> friendSettingList) {
        friendSettingList.forEach(item -> {
            LambdaQueryWrapper<SysUserFriendSetting> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUserFriendSetting::getTagKey, item.getTagKey());
            wrapper.eq(SysUserFriendSetting::getUserId, item.getUserId());
            wrapper.eq(SysUserFriendSetting::getIsDeleted, LogicDeleteEnum.IS_DELETE.getCode());
            saveOrUpdate(item, wrapper);
        });
    }
}
