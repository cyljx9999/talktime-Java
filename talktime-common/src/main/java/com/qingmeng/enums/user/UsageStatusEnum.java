package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 在线状态枚举
 * @createTime 2023年11月11日 11:24:00
 */
@Getter
@AllArgsConstructor
public enum UsageStatusEnum {
    /**
     * 使用状态枚举
     */
    OFF_LINE(0, "离线"),
    ON_LINE(1, "在线"),
    ;

    private final Integer code;
    private final String msg;
}
