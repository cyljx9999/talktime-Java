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
public enum ResultEnum implements CommonEnum {

    /**
     * 请求状态枚举
     */
    REQUEST_SUCCESS(200, "请求成功"),
    REQUEST_ERROR(500, "请求异常"),
    REQUEST_FREQUENT(500,"请求过于频繁"),
    REQUEST_REPEAT(500,"请求重复"),
    REQUEST_PARAM_ILLEGAL(500,"请求参数有误")
    ;

    private final int code;
    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
