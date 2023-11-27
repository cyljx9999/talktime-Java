package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群名称展示枚举
 * @createTime 2023年11月27日 10:37:16
 */
@Getter
@AllArgsConstructor
public enum DisplayNameStatusEnum {

    /**
     * 隐藏
     */
    CONCEAL(0, "隐藏"),

    /**
     * 展示
     */
    DISPLAY(1, "展示");

    private final Integer code;

    private final String desc;
}
