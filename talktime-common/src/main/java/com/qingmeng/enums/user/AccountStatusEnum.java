package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 账号状态枚举
 * @createTime 2023年11月11日 11:28:00
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {
    /**
     * 账号状态枚举
     */
    NORMAL(0, "正常"),
    BANNED(1, "封禁"),
    ;

    private final Integer code;
    private final String msg;
}
