package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息状态枚举
 *
 * @author qingmeng
 * @date 2024/06/09 20:15:39
 */
@AllArgsConstructor
@Getter
public enum MessageStatusEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 删除
     */
    DELETE(1, "删除"),
    ;

    private final Integer code;
    private final String desc;

    private static final Map<Integer, MessageStatusEnum> CACHE;

    static {
        CACHE = Arrays.stream(MessageStatusEnum.values()).collect(Collectors.toMap(MessageStatusEnum::getCode, Function.identity()));
    }

    public static MessageStatusEnum of(Integer type) {
        return CACHE.get(type);
    }
}
