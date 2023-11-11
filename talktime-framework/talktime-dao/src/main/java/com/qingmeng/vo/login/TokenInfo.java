package com.qingmeng.vo.login;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description token信息
 * @createTime 2023年11月10日 22:38:00
 */
@Data
public class TokenInfo {

    /**
     * token名字
     */
    private String tokenName;

    /**
     * token值
     */
    private String tokenValue;

    /**
     * 过期时间
     */
    public Long tokenTimeout;

}
