package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 开启或关闭状态枚举
 * @createTime 2023年11月27日 10:45:00
 */
@Getter
@AllArgsConstructor
public enum CloseOrOpenStatusEnum {

    /**
     * 关闭
     */
    CLOSE(0, "关闭"),

    /**
     * 开启
     */
    OPEN(1, "开启");

    private final Integer code;

    private final String desc;
}
