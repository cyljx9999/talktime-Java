package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆设备枚举
 * @createTime 2023年11月20日 09:24:21
 */
@Getter
@AllArgsConstructor
public enum LoginDeviceEnum {
    /**
     * 登陆设备枚举
     */
    PC(0, "pc"),
    H5(1, "h5"),
    APP(2, "app"),
    MINI_PROGRAM(3, "miniProgram"),
    ;

    private final Integer code;
    private final String device;
}
