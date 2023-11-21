package com.qingmeng.service;

import com.qingmeng.entity.SysUserAuth;

/**
 * <p>
 * 第三方授权登陆表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysUserAuthService{

    /**
     * 使用 Open ID 获取授权信息
     *
     * @param openId 开放 ID
     * @return {@link SysUserAuth }
     * @author qingmeng
     * @createTime: 2023/11/20 08:36:15
     */
    SysUserAuth getAuthInfoWithOpenId(String openId);

    /**
     * 保存
     *
     * @param saveUserAuth 保存用户身份验证
     * @author qingmeng
     * @createTime: 2023/11/20 08:41:53
     */
    void save(SysUserAuth saveUserAuth);
}
