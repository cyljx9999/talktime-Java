package com.qingmeng.config.strategy.login;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.vo.login.TokenInfoVO;

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
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/10 22:40:40
     */
    TokenInfoVO getTokenInfo(LoginParamDTO loginParamDTO);
}
