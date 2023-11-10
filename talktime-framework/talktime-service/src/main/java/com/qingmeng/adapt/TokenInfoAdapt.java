package com.qingmeng.adapt;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.qingmeng.domain.vo.login.TokenInfo;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description token信息设适配类
 * @createTime 2023年11月10日 22:57:00
 */
public class TokenInfoAdapt {

    /**
     * 构造token信息类
     *
     * @param saTokenInfo satoken对象
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/10 22:59:23
     */
    public static TokenInfo buildTokenInfo(SaTokenInfo saTokenInfo){
        TokenInfo info = new TokenInfo();
        info.setTokenName(saTokenInfo.getTokenName());
        info.setTokenValue(saTokenInfo.getTokenValue());
        info.setTokenTimeout(saTokenInfo.getTokenTimeout());
        return info;
    }

}
