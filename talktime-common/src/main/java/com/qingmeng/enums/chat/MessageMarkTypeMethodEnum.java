package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息类型枚举
 * @createTime 2024年06月08日 00:23:00
 */
@Getter
@AllArgsConstructor
public enum MessageMarkTypeMethodEnum {
    /**
     * 消息类型枚举
     */
    LIKE("like", "likeStrategy"),
    DIS_LIKE("disLike", "disLikeStrategy"),
    ;

    private final String type;
    private final String value;

    /**
     * 枚举值的缓存映射。
     */
    private static final Map<String, MessageMarkTypeMethodEnum> CACHE;

    static {
        // 构建角色ID到枚举值的映射缓存
        CACHE = Arrays.stream(MessageMarkTypeMethodEnum.values()).collect(Collectors.toMap(MessageMarkTypeMethodEnum::getType, Function.identity()));
    }

    /**
     * 根据登陆type查找对应的登陆枚举值。
     *
     * @param type 登陆类型
     * @return 对应的登陆类型枚举值，如果未找到则返回null
     */
    public static MessageMarkTypeMethodEnum get(String type) {
        return CACHE.get(type);
    }
}
