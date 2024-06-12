package com.qingmeng.enums.sensitive;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 敏感词类型
 * @createTime 2023年11月10日 15:10:00
 */
@Getter
@AllArgsConstructor
public enum SensitiveTypeEnum {
    /**
     * 类型
     */
    BLACK(0,"黑名单"),
    WHITE(1,"白名单"),
    ;

    private final int code;
    private final String msg;

    private final static Map<Integer, SensitiveTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(SensitiveTypeEnum.values()).collect(Collectors.toMap(SensitiveTypeEnum::getCode, Function.identity()));
    }

    public static SensitiveTypeEnum of(Integer type) {
        return CACHE.get(type);
    }

}
