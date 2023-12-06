package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群组角色
 * @createTime 2023年11月11日 12:42:00
 */
@Getter
@AllArgsConstructor
public enum GroupRoleEnum {
    /**
     * 群组角色枚举
     */
    GROUP_OWNER(0, "GroupOwner"),
    GROUP_ADMINISTRATOR(1, "Management"),
    ;

    private final Integer code;
    private final String msg;


}
