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
 * @createTime 2024年06月04日 20:56:00
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    /**
     * 发短信
     */
    TEXT(1, "正常消息","text"),
    /**
     * 撤回消息
     */
    RECALL(2, "撤回消息","recall"),
    /**
     * 图片
     */
    IMG(3, "图片","img"),
    /**
     * 文件
     */
    FILE(4, "文件","file"),
    /**
     * 语音
     */
    SOUND(5, "语音","sound"),
    /**
     * 视频
     */
    VIDEO(6, "视频","video"),
    /**
     * 表情符号
     */
    EMOJI(7, "表情","emojis"),
    /**
     * 系统
     */
    SYSTEM(8, "系统消息","system"),
    ;

    private final Integer type;
    private final String desc;
    private final String strategyMethod;

    private static final Map<Integer, MessageTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(MessageTypeEnum.values())
                .collect(Collectors.toMap(MessageTypeEnum::getType, Function.identity()));
    }

    public static MessageTypeEnum of(Integer type) {
        return CACHE.get(type);
    }
}
