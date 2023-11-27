package com.qingmeng.service.impl;

import com.qingmeng.dao.SysUserPrivacySettingDao;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.service.SysUserPrivacySettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月27日 10:27:00
 */
@Service
public class SysUserPrivacySettingServiceImpl implements SysUserPrivacySettingService {
    @Resource
    private SysUserPrivacySettingDao sysUserPrivacySettingDao;

    /**
     * 保存
     *
     * @param sysUserPrivacySetting SYS 用户隐私设置
     * @author qingmeng
     * @createTime: 2023/11/27 11:02:53
     */
    @Override
    public void save(SysUserPrivacySetting sysUserPrivacySetting) {
        sysUserPrivacySettingDao.save(sysUserPrivacySetting);
    }
}
