package com.qingmeng.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 开关状态枚举
 * @createTime 2023年11月10日 15:10:00
 */
@Getter
@AllArgsConstructor
public enum OpenStatusEnum {
    /**
     * 操作状态
     */
    CLOSE(0,"关闭"),
    OPEN(1,"启动"),
    ;

    private final int code;
    private final String msg;

    private final static Map<Integer, OpenStatusEnum> CACHE;

    static {
        CACHE = Arrays.stream(OpenStatusEnum.values()).collect(Collectors.toMap(OpenStatusEnum::getCode, Function.identity()));
    }

    public static OpenStatusEnum of(Integer type) {
        return CACHE.get(type);
    }

}
