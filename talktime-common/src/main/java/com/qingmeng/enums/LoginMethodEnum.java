package com.qingmeng.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆枚举
 * @createTime 2023年11月11日 00:23:00
 */
@Getter
@AllArgsConstructor
public enum LoginMethodEnum {
    /**
     * 登陆类型
     */
    ACCOUNT("account", "accountStrategy"),
    PHONE("phone", "phoneStrategy")
    ;

    private final String type;
    private final String value;

    /**
     * 枚举值的缓存映射。
     */
    private static final Map<String, LoginMethodEnum> CACHE;

    static {
        // 构建角色ID到枚举值的映射缓存
        CACHE = Arrays.stream(LoginMethodEnum.values()).collect(Collectors.toMap(LoginMethodEnum::getType, Function.identity()));
    }

    /**
     * 根据登陆type查找对应的登陆枚举值。
     *
     * @param type 登陆类型
     * @return 对应的登陆类型枚举值，如果未找到则返回null
     */
    public static LoginMethodEnum get(String type) {
        return CACHE.get(type);
    }
}
