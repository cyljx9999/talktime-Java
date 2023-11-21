package com.qingmeng.dto.login;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆参数服务
 * @createTime 2023年11月21日 16:48:00
 */
@Data
public class LoginMessageDTO {

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 登录代码
     */
    private Integer loginCode;

}
