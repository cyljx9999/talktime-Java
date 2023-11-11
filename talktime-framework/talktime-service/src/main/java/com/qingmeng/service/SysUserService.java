package com.qingmeng.service;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.vo.login.TokenInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysUserService{

    /**
     * 登陆
     *
     * @param paramDTO 登陆参数对象
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/11 00:49:54
     */
    TokenInfo login(LoginParamDTO paramDTO);
}
