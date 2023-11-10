package com.qingmeng.exception;

import com.qingmeng.enums.CommonEnum;
import com.qingmeng.enums.ResultEnum;
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
     * @param commonEnum 异常枚举值
     */
    public TalkTimeException(CommonEnum commonEnum) {
        super();
        this.code = commonEnum.getCode();
        this.message = commonEnum.getMsg();
    }

    /**
     * 自定义异常消息处理
     * @param message 异常信息
     */
    public TalkTimeException(String message) {
        super();
        this.code = ResultEnum.REQUEST_ERROR.getCode();
        this.message = message;
    }

    /**
     * 自定义异常消息处理
     *
     * @param code 异常码
     * @param message 异常信息
     */
    public TalkTimeException(int code,String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 自定义异常消息处理
     *
     * @param commonEnum 异常枚举值
     * @param data 异常数据
     */
    public TalkTimeException(CommonEnum commonEnum, Object data) {
        super();
        this.code = commonEnum.getCode();
        this.message = commonEnum.getMsg();
        this.data = data;
    }
}
