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
 * @Description
 * @createTime 2023年11月13日 10:39:00
 */
@Getter
@AllArgsConstructor
public enum MessageMarkTypeEnum {
    /**
     * 标记类型：1 表示点赞，2 表示举报
     */
    UPVOTE(1,"点赞",10,"like"),
    REPORT(2,"举报",5,"disLike")
    ;

    private final Integer code;
    private final String msg;
    private final Integer riseNum;
    private final String strategyMethod;

    private static final Map<Integer, MessageMarkTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(MessageMarkTypeEnum.values()).collect(Collectors.toMap(MessageMarkTypeEnum::getCode, Function.identity()));
    }

    public static MessageMarkTypeEnum of(Integer type) {
        return CACHE.get(type);
    }

}
