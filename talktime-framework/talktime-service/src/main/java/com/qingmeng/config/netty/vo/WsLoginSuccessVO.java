package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆成功
 * @createTime 2023年11月13日 10:35:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsLoginSuccessVO {
    /**
     * 用户唯一标识符。
     */
    private Long userId;

    /**
     * 用户头像 URL。
     */
    private String avatar;

    /**
     * 用户登录成功后生成的令牌名字。
     */
    private String tokenName;

    /**
     * 用户登录成功后生成的令牌。
     */
    private String tokenValue;

    /**
     * 用户名字。
     */
    private String userName;

}
