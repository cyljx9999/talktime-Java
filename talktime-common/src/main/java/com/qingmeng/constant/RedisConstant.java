package com.qingmeng.constant;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description redis key常量类
 * @createTime 2023年11月11日 14:30:00
 */
public class RedisConstant {

    /**
     * 验证码key以及对应的过期时间
     */
    public static final String CAPTCHA_CODE_KEY = "captcha:code:";
    public static final long CAPTCHA_CODE_EXPIRE = 2L;
}
