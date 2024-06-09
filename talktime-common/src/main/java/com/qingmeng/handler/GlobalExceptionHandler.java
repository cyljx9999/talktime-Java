package com.qingmeng.handler;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.enums.system.ResultEnum;
import com.qingmeng.enums.system.TokenEnum;
import com.qingmeng.exception.TalkTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 全局异常统一处理
 * @createTime 2023年11月09日 22:33:00
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理校验单个字段失败异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<Map<Path, String>> handleValidException(ConstraintViolationException e){

        // 获取异常信息
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        // 将异常信息收集到Map，key为校验失败的字段，value为失败原因
        Map<Path, String> errorMap = constraintViolations.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        log.info("Error--->{}",errorMap);
        // 返回校验失败信息
        return CommonResult.error(ResultEnum.REQUEST_PARAM_ILLEGAL,errorMap);
    }

    /**
     * 处理验证异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public CommonResult<Map<String, String>> handleValidateException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>(8);
        // 将校验错误字段和错误信息提取到map中
        bindingResult.getFieldErrors().forEach(item -> errorMap.put(item.getField(),item.getDefaultMessage()));
        log.error(String.valueOf(errorMap));
        return CommonResult.error(ResultEnum.REQUEST_PARAM_ILLEGAL,errorMap);

    }

    /**
     * 处理验证exception
     */
    @ExceptionHandler({BindException.class})
    public CommonResult<Map<String, String>> handleValidateException1(BindException e) {

        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>(8);
        // 将校验错误字段和错误信息提取到map中
        bindingResult.getFieldErrors().forEach(item -> errorMap.put(item.getField(),item.getDefaultMessage()));

        return CommonResult.error(ResultEnum.REQUEST_PARAM_ILLEGAL,errorMap);

    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler({TalkTimeException.class})
    public CommonResult<?> shopExceptionHandler(TalkTimeException e) {
        return CommonResult.error(e.getCode(),e.getMessage(),e.getData());
    }

    /**
     * 处理登录异常
     */
    @ExceptionHandler({NotLoginException.class})
    public CommonResult<?> handlerNotLoginException(NotLoginException notLoginException){
        // 判断场景值，定制化异常信息
        String message;
        if(notLoginException.getType().equals(NotLoginException.NOT_TOKEN)) {
            return CommonResult.error(TokenEnum.NOT_TOKEN);
        }
        else if(notLoginException.getType().equals(NotLoginException.INVALID_TOKEN)) {
            return CommonResult.error(TokenEnum.INVALID_TOKEN);
        }
        else if(notLoginException.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            return CommonResult.error(TokenEnum.TOKEN_TIMEOUT);
        }
        else if(notLoginException.getType().equals(NotLoginException.BE_REPLACED)) {
            return CommonResult.error(TokenEnum.BE_REPLACED);
        }
        else if(notLoginException.getType().equals(NotLoginException.KICK_OUT)) {
            return CommonResult.error(TokenEnum.KICK_OUT);
        }
        else {
            message = "未登录";
        }

        return CommonResult.error(TokenEnum.TOKEN_ERROR.getCode(), message);
    }

    /**
     * 处理封禁异常
     */
    @ExceptionHandler({DisableServiceException.class})
    public CommonResult<DisableServiceException> disableServiceExceptionHandler(DisableServiceException disableServiceException) {
        return CommonResult.error(disableServiceException.getMessage());
    }

    /**
     * 处理权限异常
     */
    @ExceptionHandler({NotPermissionException.class})
    public CommonResult<NotPermissionException> notPermissionExceptionExceptionHandler(NotPermissionException notPermissionException) {
        return CommonResult.error(notPermissionException.getMessage());
    }


    /**
     * 请求方式错误
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public CommonResult<MethodArgumentTypeMismatchException> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException err) {
        return CommonResult.error(ResultEnum.REQUEST_ERROR,err);
    }

    /**
     * 请求参数的类型错误
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public CommonResult<HttpMessageNotReadableException> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return CommonResult.error(ResultEnum.REQUEST_ERROR,e);
    }


    /**
     * 处理其他异常
     */
    @ExceptionHandler({Exception.class})
    public CommonResult<Exception> handleOtherException(Exception e) {
        log.error("-------------------------------------------------------------------------");
        log.error(e.getMessage()+"------"+e.getClass());
        log.error(String.valueOf(e.getClass()));
        e.printStackTrace();
        return CommonResult.error("系统未知异常，请联系管理员");
    }
}
