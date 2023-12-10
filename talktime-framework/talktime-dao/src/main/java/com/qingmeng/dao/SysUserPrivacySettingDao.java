package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.user.PersonalPrivacySettingDTO;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.mapper.SysUserPrivacySettingMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户隐私设置表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:17:01
 */
@Service
public class SysUserPrivacySettingDao extends ServiceImpl<SysUserPrivacySettingMapper, SysUserPrivacySetting>{

    /**
     * 更改个人隐私设置
     *
     * @param userId                    用户 ID
     * @param personalPrivacySettingDTO 个人隐私设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/02 11:36:49
     */
    public void alterPersonalPrivacySetting(Long userId, PersonalPrivacySettingDTO personalPrivacySettingDTO) {
        lambdaUpdate()
                .eq(SysUserPrivacySetting::getUserId, userId)
                .set(Objects.nonNull(personalPrivacySettingDTO.getAddByAccount()), SysUserPrivacySetting::getAddByAccount, personalPrivacySettingDTO.getAddByAccount())
                .set(Objects.nonNull(personalPrivacySettingDTO.getAddByCard()), SysUserPrivacySetting::getAddByCard, personalPrivacySettingDTO.getAddByCard())
                .set(Objects.nonNull(personalPrivacySettingDTO.getAddByGroup()), SysUserPrivacySetting::getAddByGroup, personalPrivacySettingDTO.getAddByGroup())
                .set(Objects.nonNull(personalPrivacySettingDTO.getAddByQrcode()), SysUserPrivacySetting::getAddByQrcode, personalPrivacySettingDTO.getAddByQrcode())
                .set(StrUtil.isNotBlank(personalPrivacySettingDTO.getPersonalizedSignature()), SysUserPrivacySetting::getPersonalizedSignature, personalPrivacySettingDTO.getPersonalizedSignature())
                .set(StrUtil.isNotBlank(personalPrivacySettingDTO.getPatContent()), SysUserPrivacySetting::getPatContent, personalPrivacySettingDTO.getPatContent())
                .update(new SysUserPrivacySetting());
    }

    /**
     * 按用户 ID 获取列表
     *
     * @param userIds 用户 ID
     * @return {@link List }<{@link SysUserPrivacySetting }>
     * @author qingmeng
     * @createTime: 2023/12/10 09:56:01
     */
    public List<SysUserPrivacySetting> getListByUserIds(List<Long> userIds) {
        return lambdaQuery().in(SysUserPrivacySetting::getUserId, userIds).list();
    }
}
