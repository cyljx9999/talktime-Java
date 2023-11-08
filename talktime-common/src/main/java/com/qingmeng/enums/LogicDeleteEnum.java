package com.qingmeng.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 逻辑删除枚举
 * @createTime 2023年11月08日 11:30:00
 */
@Getter
@AllArgsConstructor
public enum LogicDeleteEnum {

    /**
     * 未删除
     */
    NOT_DELETE(0, "未删除"),

    /**
     * 已删除
     */
    IS_DELETE(1, "已删除");

    private final Integer code;

    private final String desc;
}
