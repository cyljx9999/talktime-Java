package com.qingmeng.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 封禁状态
 * @createTime 2023年11月22日 07:48:00
 */
@Getter
@AllArgsConstructor
public enum BannedEnum {

    /**
     * 封禁类型
     */
    LOGIN("登录"),
    LOTTERY("抽奖"),
    GROUP_CHAT("群聊"),
    ;

    private final String status;
}
