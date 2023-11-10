package com.qingmeng.strategy.login;

import com.qingmeng.domain.dto.login.LoginParamDTO;
import com.qingmeng.domain.vo.login.TokenInfo;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登录通用接口
 * @createTime 2023年11月10日 22:36:00
 */
public interface LoginStrategy {



    /**
     * 登陆获取token方法
     *
     * @param loginParamDTO 登陆参数
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/10 22:40:40
     */
    TokenInfo getTokenInfo(LoginParamDTO loginParamDTO);
}
