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
 * @Description 上传场景类型枚举
 * @createTime 2023年12月05日 22:47:00
 */
@AllArgsConstructor
@Getter
public enum UploadSceneEnum {
    /**
     * 场景类型
     */
    CHAT(1, "聊天", "/chat"),
    EMOJI(2, "表情包", "/emoji"),
    QRCODE(3, "二维码", "/qrcode"),
    ;

    private final Integer type;
    private final String desc;
    private final String path;

    private static final Map<Integer, UploadSceneEnum> CACHE;

    static {
        CACHE = Arrays.stream(UploadSceneEnum.values()).collect(Collectors.toMap(UploadSceneEnum::getType, Function.identity()));
    }

    public static UploadSceneEnum get(Integer type) {
        return CACHE.get(type);
    }
}
