package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 性别枚举
 * @createTime 2023年11月11日 11:22:00
 */
@Getter
@AllArgsConstructor
public enum SexEnum {
    /**
     * 性别枚举
     */
    FEMALE(0, "女"),
    MALE(1, "男"),
    ;

    private final Integer code;
    private final String msg;
}
