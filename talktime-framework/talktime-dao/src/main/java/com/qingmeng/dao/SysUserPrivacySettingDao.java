package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.user.PersonalPrivacySettingDTO;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.mapper.SysUserPrivacySettingMapper;
import org.springframework.stereotype.Service;

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

    }
}
