package com.qingmeng.domain.rep;

import com.qingmeng.enums.system.CommonEnum;
import com.qingmeng.enums.system.ResultEnum;
import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 通用返回类
 * @createTime 2023年11月09日 22:35:00
 */
@Data
public class CommonResult<T> {

    /**
     * 返回成功失败标示
     */
    private Integer code;

    /**
     * 返回成功失败信息
     */
    private String msg;

    /**
     * 返回成功失败附加数据
     */
    private T data;

    public CommonResult() {
    }

    public CommonResult(Integer code)
    {
        super();
        this.code = code;
    }

    public CommonResult(Integer code, String msg)
    {
        super();
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(Integer code, String msg, T data)
    {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultEnum.REQUEST_SUCCESS.getCode(), ResultEnum.REQUEST_SUCCESS.getMsg());
    }

    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult<>(ResultEnum.REQUEST_SUCCESS.getCode(), msg);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultEnum.REQUEST_SUCCESS.getCode(), ResultEnum.REQUEST_SUCCESS.getMsg(),data);
    }

    public static <T> CommonResult<T> success(CommonEnum commonEnum)
    {
        return new CommonResult<>(commonEnum.getCode(), commonEnum.getMsg());
    }

    public static <T> CommonResult<T> success(String msg,T data) {
        return new CommonResult<>(ResultEnum.REQUEST_SUCCESS.getCode(), msg,data);
    }


    public static <T> CommonResult<T> success(CommonEnum commonEnum,T data)
    {
        return new CommonResult<>(commonEnum.getCode(), commonEnum.getMsg(),data);
    }


    public static <T> CommonResult<T> error() {
        return new CommonResult<>(ResultEnum.REQUEST_ERROR.getCode(), ResultEnum.REQUEST_ERROR.getMsg());
    }

    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<>(ResultEnum.REQUEST_ERROR.getCode(), msg);
    }

    public static <T> CommonResult<T> error(Integer code ,String msg) {
        return new CommonResult<>(code, msg);
    }

    public static <T> CommonResult<T> error(CommonEnum commonEnum)
    {
        return new CommonResult<>(commonEnum.getCode(), commonEnum.getMsg());
    }


    public static <T> CommonResult<T> error(Integer code ,String msg, T data) {
        return new CommonResult<>(code, msg, data);
    }

    public static <T> CommonResult<T> error(String msg, T data) {
        return new CommonResult<>(ResultEnum.REQUEST_ERROR.getCode(), msg, data);
    }

    public static <T> CommonResult<T> error(CommonEnum commonEnum, T data) {
        return new CommonResult<>(commonEnum.getCode(), commonEnum.getMsg(), data);
    }


}