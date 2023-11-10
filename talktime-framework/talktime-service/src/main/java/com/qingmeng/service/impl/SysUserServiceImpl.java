package com.qingmeng.service.impl;

import com.qingmeng.domain.dto.login.LoginParamDTO;
import com.qingmeng.domain.vo.login.TokenInfo;
import com.qingmeng.enums.LoginTypeEnum;
import com.qingmeng.service.SysUserService;
import com.qingmeng.strategy.login.LoginFactory;
import com.qingmeng.strategy.login.LoginStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:19:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private LoginFactory loginFactory;

    /**
     * 登陆
     *
     * @param paramDTO 登陆参数对象
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/11 00:49:54
     */
    @Override
    public TokenInfo login(LoginParamDTO paramDTO) {
        LoginTypeEnum typeEnum = LoginTypeEnum.get(paramDTO.getLoginMethod());
        LoginStrategy loginStrategy = loginFactory.getStrategyWithType(typeEnum.getValue());
        return loginStrategy.getTokenInfo(paramDTO);
    }
}
