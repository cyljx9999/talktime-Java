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

    /**
     * 手机验证码key以及对应的过期时间
     */
    public static final String PHONE_CODE_KEY = "phone:";
    public static final long PHONE_CODE_EXPIRE = 2L;

    /**
     * 扫码用户登陆code
     */
    public static final String OPEN_ID_CODE = "openId:code";
    public static final int OPEN_ID_CODE_EXPIRE = 60;

    /**
     * 用户缓存key和过期时间
     */
    public static final String USER_INFO_KEY = "userinfo:";
    public static final long USER_INFO_EXPIRE = 5L;

    /**
     * 在线用户列表
     */
    public static final String ONLINE_USERID_KEY = "online";

    /**
     * 离线用户列表
     */
    public static final String OFFLINE_USERID_KEY = "offline";
}
