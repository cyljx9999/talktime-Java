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
    TEXT(1, "正常消息"),
    /**
     * 撤回消息
     */
    RECALL(2, "撤回消息"),
    /**
     * 图片
     */
    IMG(3, "图片"),
    /**
     * 文件
     */
    FILE(4, "文件"),
    /**
     * 语音
     */
    SOUND(5, "语音"),
    /**
     * 视频
     */
    VIDEO(6, "视频"),
    /**
     * 表情符号
     */
    EMOJI(7, "表情"),
    /**
     * 系统
     */
    SYSTEM(8, "系统消息"),
    ;

    private final Integer type;
    private final String desc;

    private static final Map<Integer, MessageTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(MessageTypeEnum.values())
                .collect(Collectors.toMap(MessageTypeEnum::getType, Function.identity()));
    }

    public static MessageTypeEnum getByType(Integer type) {
        return CACHE.get(type);
    }
}
