package com.qingmeng.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 操作状态枚举
 * @createTime 2023年11月10日 15:10:00
 */
@Getter
@AllArgsConstructor
public enum OperateEnums {
    /**
     * 操作状态
     */
    SUCCESS(0,"成功"),
    ERROR(1,"失败"),
    ;

    private final int code;
    private final String msg;

}
