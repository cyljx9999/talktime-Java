package com.qingmeng.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 系统常量
 * @createTime 2023年11月10日 21:37:00
 */
public class SystemConstant {
    /**
     * 本地ip
     */
    public static final String LOCAL_IP = "127.0.0.1";

    /**
     * 万能验证码
     */
    public static final String UNIVERSAL_VERIFICATION_CODE = "qingmeng";

    /**
     * 密码加盐
     */
    public static final String MD5_SALT = "talktime";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "talktime123456";

    /**
     *  WebSocket 服务器监听的端口号
     */
    public static final int WEB_SOCKET_PORT = 9111;

    /**
     * 重试间隔分钟
     */
    public static final double RETRY_INTERVAL_MINUTES = 2D;

    /**
     * 最大重试次数
     */
    public static final int MAX_RETRY_COUNT = 3;

    /**
     * 秒
     */
    public static final int SECOND = 60;

    /**
     * 分钟
     */
    public static final int MINUTE = 60 * 60;

    /**
     * 日
     */
    public static final int DAY = 24 * 60 * 60;

    /**
     * 字母列表
     */
    public static final List<String> ALPHABET_LIST = Arrays.asList(
            "A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U",
            "V","W","X","Y","Z","#"
    );

    /**
     * 昵称最大长度
     */
    public static final int NICK_NAME_MAX_LENGTH = 10;

    /**
     * QR码宽度
     */
    public static final int QRCODE_WIDTH = 300;

    /**
     * QR码高度
     */
    public static final int QRCODE_HEIGHT = 300;


}
