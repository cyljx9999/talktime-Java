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
 * @Description 消息状态
 * @createTime 2024年06月06日 11:32:00
 */
@Getter
@AllArgsConstructor
public enum ChatMessageStatusEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 删除
     */
    DELETE(1, "删除"),
    ;

    private final Integer status;
    private final String desc;

    private final static Map<Integer, ChatMessageStatusEnum> CACHE;

    static {
        CACHE = Arrays.stream(ChatMessageStatusEnum.values()).collect(Collectors.toMap(ChatMessageStatusEnum::getStatus, Function.identity()));
    }

    public static ChatMessageStatusEnum of(Integer type) {
        return CACHE.get(type);
    }
}
