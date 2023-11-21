package com.qingmeng.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ws主动认证参数
 * @createTime 2023年11月21日 19:34:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsAuthorizeDTO {

    /**
     * 令 牌
     */
    private String token;

}
