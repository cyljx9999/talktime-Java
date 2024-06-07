package com.qingmeng.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 是或否枚举
 *
 * @author qingmeng
 * @date 2024/06/07 23:25:10
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {
    /**
     * 不
     */
    NO(0, "否"),
    /**
     * 是
     */
    YES(1, "是"),
    ;

    private final Integer code;
    private final String desc;

    private static final Map<Integer, YesOrNoEnum> CACHE;

    static {
        CACHE = Arrays.stream(YesOrNoEnum.values()).collect(Collectors.toMap(YesOrNoEnum::getCode, Function.identity()));
    }

    public static YesOrNoEnum of(Integer type) {
        return CACHE.get(type);
    }

    public static Integer toStatus(Boolean bool) {
        return bool ? YES.getCode() : NO.getCode();
    }
}
