package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友状态枚举
 * @createTime 2023年11月11日 12:47:00
 */
@Getter
@AllArgsConstructor
public enum FriendStausEnum {
    /**
     * 好友状态枚举枚举
     */
    NORMAL(0, "正常"),
    BLOCK(1, "拉黑"),
    ;

    private final Integer code;
    private final String msg;
}
