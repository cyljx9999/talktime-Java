package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 房间状态枚举
 * @createTime 2023年11月27日 10:32:36
 */
@Getter
@AllArgsConstructor
public enum RoomStatusEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 封禁
     */
    BANNED(1, "封禁");

    private final Integer code;

    private final String desc;
}
