package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 房间类型枚举
 * @createTime 2023年11月27日 10:41:36
 */
@Getter
@AllArgsConstructor
public enum RoomTypeEnum {

    /**
     * 好友
     */
    FRIEND(0, "好友"),

    /**
     * 群组
     */
    GROUP(1, "群组");

    private final Integer code;

    private final String desc;
}
