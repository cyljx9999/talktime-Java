package com.qingmeng.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 操作状态枚举
 * @createTime 2023年11月10日 15:10:00
 */
@Getter
@AllArgsConstructor
public enum OperateEnum {
    /**
     * 操作状态
     */
    SUCCESS(0,"成功"),
    ERROR(1,"失败"),
    ;

    private final int code;
    private final String msg;

    private final static Map<Integer, OperateEnum> CACHE;

    static {
        CACHE = Arrays.stream(OperateEnum.values()).collect(Collectors.toMap(OperateEnum::getCode, Function.identity()));
    }

    public static OperateEnum of(Integer type) {
        return CACHE.get(type);
    }

    public static Integer toStatus(Boolean bool) {
        return bool ? OperateEnum.SUCCESS.getCode() : OperateEnum.ERROR.getCode();
    }

}
