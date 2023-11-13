package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群成员变动
 * @createTime 2023年11月13日 10:49:00
 */
@Getter
@AllArgsConstructor
public enum MemberChangeEnum {
    /**
     * 0加入 1移除
     */
    add_group(1,"加入群聊"),
    remove_group(2,"移除群聊"),
    ;

    private final Integer code;
    private final String msg;

}
