package com.qingmeng.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友申请状态
 * @createTime 2023年11月11日 12:45:00
 */
@Getter
@AllArgsConstructor
public enum ApplyStatusEnum {
    /**
     * 好友申请状态枚举
     */
    APPLYING(0, "申请中"),
    ACCEPT(1, "接受"),
    REJECT(2, "拒绝"),
    BLOCK(3, "拉黑"),
    ;

    private final Integer code;
    private final String msg;

}
