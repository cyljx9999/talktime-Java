package com.qingmeng.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description token类型枚举
 * @createTime 2023年11月09日 22:37:00
 */
@Getter
@AllArgsConstructor
public enum TokenEnums implements CommonEnum {

    /**
     * token异常
     */
    NOT_TOKEN(600, "未能从请求中读取到token"),
    INVALID_TOKEN(601, "token无效"),
    TOKEN_TIMEOUT(602, "token已经过期"),
    BE_REPLACED(603, "token已被顶下线"),
    KICK_OUT(604, "token已被踢下线"),
    TOKEN_ERROR(605, "token异常"),
    ;

    private final int code;
    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
