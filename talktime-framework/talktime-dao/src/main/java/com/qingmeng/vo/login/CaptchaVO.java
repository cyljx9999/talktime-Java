package com.qingmeng.vo.login;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 验证码返回类
 * @createTime 2023年11月11日 14:26:00
 */
@Data
public class CaptchaVO {

    /**
     * 验证码
     */
    private String code;

    /**
     * 验证码绑定id
     */
    private String codeId;

}
