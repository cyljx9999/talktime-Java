package com.qingmeng.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 执行状态枚举
 * @createTime 2023年11月20日 15:24:00
 */
@Getter
@AllArgsConstructor
public enum InvokeEnum {
    /**
     * 执行状态
     */
    WAITING(1,"等待执行"),
    FAIL(2,"失败"),
    ;

    private final int code;
    private final String msg;
}
