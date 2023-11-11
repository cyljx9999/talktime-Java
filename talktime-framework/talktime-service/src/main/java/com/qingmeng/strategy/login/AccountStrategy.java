package com.qingmeng.strategy.login;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.vo.login.TokenInfo;
import com.qingmeng.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 账号登陆实现类
 * @createTime 2023年11月11日 00:11:00
 */
@Component
public class AccountStrategy extends AbstractLoginStrategy{

    /**
     * 登陆获取token方法
     *
     * @param loginParamDTO 登陆参数
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/10 22:40:40
     */
    @Override
    public TokenInfo getTokenInfo(LoginParamDTO loginParamDTO) {
        checkParam(loginParamDTO);
        SysUser sysUser = getUserInfo(loginParamDTO);
        return createToken(sysUser,loginParamDTO.getLoginType(),loginParamDTO.getFlag());
    }
}
