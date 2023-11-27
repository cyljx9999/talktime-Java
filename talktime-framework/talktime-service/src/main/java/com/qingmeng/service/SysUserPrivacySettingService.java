package com.qingmeng.service;

import com.qingmeng.entity.SysUserPrivacySetting;

/**
 * <p>
 * 用户隐私设置表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:17:01
 */
public interface SysUserPrivacySettingService{

    /**
     * 保存
     *
     * @param sysUserPrivacySetting SYS 用户隐私设置
     * @author qingmeng
     * @createTime: 2023/11/27 11:02:53
     */
    void save(SysUserPrivacySetting sysUserPrivacySetting);
}
