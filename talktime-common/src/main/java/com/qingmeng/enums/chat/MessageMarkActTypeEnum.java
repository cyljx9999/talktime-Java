package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 动作类型
 * @createTime 2023年11月13日 10:42:00
 */
@Getter
@AllArgsConstructor
public enum MessageMarkActTypeEnum  {
    /**
     * 动作类型：1 确认，2 取消
     */
    CONFIRM(1,"确认"),
    CANCEL(2,"取消"),
    ;

    private final Integer code;
    private final String msg;
}
