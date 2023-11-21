package com.qingmeng.service.impl;

import com.qingmeng.dao.SysUserAuthDao;
import com.qingmeng.entity.SysUserAuth;
import com.qingmeng.service.SysUserAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:19:00
 */
@Service
public class SysUserAuthServiceImpl implements SysUserAuthService {
    @Resource
    private SysUserAuthDao sysUserAuthDao;

    /**
     * 使用 Open ID 获取授权信息
     *
     * @param openId 开放 ID
     * @return {@link SysUserAuth }
     * @author qingmeng
     * @createTime: 2023/11/20 08:36:15
     */
    @Override
    public SysUserAuth getAuthInfoWithOpenId(String openId) {
        return sysUserAuthDao.getInfoByOpenId(openId);
    }

    /**
     * 保存
     *
     * @param saveUserAuth 保存用户身份验证
     * @author qingmeng
     * @createTime: 2023/11/20 08:41:53
     */
    @Override
    public void save(SysUserAuth saveUserAuth) {
        sysUserAuthDao.save(saveUserAuth);
    }
}
