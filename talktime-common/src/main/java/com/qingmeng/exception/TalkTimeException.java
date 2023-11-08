package com.qingmeng.exception;

import com.qingmeng.enums.ResultEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义异常
 * @createTime 2023年11月08日 20:58:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TalkTimeException extends RuntimeException{
    private static final long serialVersionUID = 1352443288764724607L;
    private int code;
    private String message;
    private Object data;

    /**
     * 自定义异常消息处理
     * @param resultEnums 异常枚举值
     */
    public TalkTimeException(ResultEnums resultEnums) {
        super();
        this.code = resultEnums.getCode();
        this.message = resultEnums.getMsg();
    }

    /**
     * 自定义异常消息处理
     * @param message 异常信息
     */
    public TalkTimeException(String message) {
        super();
        this.code = ResultEnums.REQUEST_ERROR.getCode();
        this.message = message;
    }

    /**
     * 自定义异常消息处理
     *
     * @param resultEnums 异常枚举值
     * @param data 异常数据
     */
    public TalkTimeException(ResultEnums resultEnums,Object data) {
        super();
        this.code = resultEnums.getCode();
        this.message = resultEnums.getMsg();
        this.data = data;
    }
}
