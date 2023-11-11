package com.qingmeng.enums.article;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 物品枚举
 * @createTime 2023年11月11日 11:30:00
 */
@Getter
@AllArgsConstructor
public enum ArticleTypeEnum {
    /**
     * 物品枚举
     */
    ALTER_NAME(0, "改名卡"),
    BADGE(1, "徽章"),
    HEAD_BORDER(2, "头像边框"),
    ;

    private final Integer code;
    private final String msg;

}
