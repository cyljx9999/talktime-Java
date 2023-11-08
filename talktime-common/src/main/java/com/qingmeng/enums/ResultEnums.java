package com.qingmeng.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 接口返回枚举类
 * @createTime 2023年11月08日 20:59:00
 */
@Getter
@AllArgsConstructor
public enum ResultEnums {

    /**
     * 请求访问状态枚举
     */
    REQUEST_SUCCESS(200, "请求成功"),
    REQUEST_ERROR(500, "请求异常"),
    REQUEST_FREQUENT(500,"请求过于频繁")
    ;

    private final int code;
    private final String msg;
}
