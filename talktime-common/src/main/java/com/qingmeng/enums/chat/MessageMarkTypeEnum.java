package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月13日 10:39:00
 */
@Getter
@AllArgsConstructor
public enum MessageMarkTypeEnum {
    /**
     * 标记类型：1 表示点赞，2 表示举报
     */
    UPVOTE(1,"点赞"),
    REPORT(2,"举报"),
    ;

    private final Integer code;
    private final String msg;
}
