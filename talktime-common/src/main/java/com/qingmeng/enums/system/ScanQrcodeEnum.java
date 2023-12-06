package com.qingmeng.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 扫码二维码类型枚举
 * @createTime 2023年12月06日 10:55:00
 */
@Getter
@AllArgsConstructor
public enum ScanQrcodeEnum {

    /**
     * 类型
     */
    friend(1,"好友"),
    group(2,"群聊"),
    ;

    private final int code;
    private final String msg;

}
