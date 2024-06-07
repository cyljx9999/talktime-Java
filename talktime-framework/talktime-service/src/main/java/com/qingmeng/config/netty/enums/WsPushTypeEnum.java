package com.qingmeng.config.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * WS 推送类型枚举
 *
 * @author qingmeng
 * @date 2024/06/08 00:15:53
 */
@AllArgsConstructor
@Getter
public enum WsPushTypeEnum {
    /**
     * 用户
     */
    USER(1, "个人"),
    ONLINE_ALL_USER(2, "已登陆用户"),
    ;

    private final Integer type;
    private final String desc;

    private static final Map<Integer, WsPushTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(WsPushTypeEnum.values()).collect(Collectors.toMap(WsPushTypeEnum::getType, Function.identity()));
    }

    public static WsPushTypeEnum of(Integer type) {
        return CACHE.get(type);
    }
}
