package com.qingmeng.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 正常枚举或无枚举
 *
 * @author qingmeng
 * @date 2024/06/07 23:09:13
 */
@AllArgsConstructor
@Getter
public enum NormalOrNoEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 不正常
     */
    NOT_NORMAL(1, "不正常"),
    ;

    private final Integer code;
    private final String desc;

    private static final Map<Integer, NormalOrNoEnum> CACHE;

    static {
        CACHE = Arrays.stream(NormalOrNoEnum.values()).collect(Collectors.toMap(NormalOrNoEnum::getCode, Function.identity()));
    }

    public static NormalOrNoEnum of(Integer type) {
        return CACHE.get(type);
    }

    public static Integer toStatus(Boolean bool) {
        return bool ? NORMAL.getCode() : NOT_NORMAL.getCode();
    }
}
