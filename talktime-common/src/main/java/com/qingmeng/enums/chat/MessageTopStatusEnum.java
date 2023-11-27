package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息置顶枚举
 * @createTime 2023年11月27日 10:35:36
 */
@Getter
@AllArgsConstructor
public enum MessageTopStatusEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 已删除
     */
    TOP(1, "置顶");

    private final Integer code;

    private final String desc;
}
