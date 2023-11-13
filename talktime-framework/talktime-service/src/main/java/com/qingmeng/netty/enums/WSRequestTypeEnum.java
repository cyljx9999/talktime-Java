package com.qingmeng.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ws请求类型
 * @createTime 2023年11月13日 09:26:00
 */
@AllArgsConstructor
@Getter
public enum WSRequestTypeEnum {

    /**
     * 请求登录二维码
     */
    LOGIN("qrcode", "请求登录二维码"),

    /**
     * 心跳状态
     */
    HEARTBEAT("heartbeat", "心跳包"),

    /**
     * 登陆认证状态
     */
    AUTHORIZE("authorization", "登录认证"),
    ;

    /**
     * 类型，用于标识不同的请求状态
     */
    private final String type;

    /**
     * 类型描述信息，用于说明每种请求状态的作用
     */
    private final String desc;

    /**
     * 缓存枚举类型，将类型和枚举对象映射起来，以便快速查找
     */
    private static final Map<String, WSRequestTypeEnum> CACHE;

    static {
        // 使用流操作将枚举值构建成类型和枚举对象的映射关系
        CACHE = Arrays.stream(WSRequestTypeEnum.values()).collect(Collectors.toMap(WSRequestTypeEnum::getType, Function.identity()));
    }

    /**
     * 根据类型返回对应的枚举类型
     * @param type 类型
     * @return {@link WSRequestTypeEnum} 对应的枚举对象
     */
    public static WSRequestTypeEnum of(String type) {
        return CACHE.get(type);
    }

}
