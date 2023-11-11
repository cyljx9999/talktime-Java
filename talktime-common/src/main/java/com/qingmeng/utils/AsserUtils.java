package com.qingmeng.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.qingmeng.enums.system.CommonEnum;
import com.qingmeng.enums.system.ResultEnum;
import com.qingmeng.exception.TalkTimeException;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 业务校验工具
 * @createTime 2023年11月09日 22:11:00
 */
public class AsserUtils {

    /**
     * SpringValidatorAdapter 实例。
     */
    public static final SpringValidatorAdapter SPRING_VALIDATOR_ADAPTER = SpringUtils.getBean(SpringValidatorAdapter.class);

    /**
     * 判断条件是否为真，如果不为真则抛出异常。
     *
     * @param flag    条件值
     * @param message 异常信息
     * @throws TalkTimeException 如果条件不为真
     */
    public static void isTrue(boolean flag, String message) {
        if (!flag) {
            throwException(null, message);
        }
    }

    /**
     * 判断条件是否为真，如果不为真则抛出异常。
     *
     * @param expression 条件值
     * @param commonEnum 异常枚举
     * @param args       异常参数
     * @throws TalkTimeException 如果条件不为真
     */
    public static void isTrue(boolean expression, CommonEnum commonEnum, Object... args) {
        if (!expression) {
            throwException(commonEnum, args);
        }
    }

    /**
     * 判断条件是否为假，如果不为假则抛出异常。
     *
     * @param expression 条件值
     * @param message    异常信息
     * @throws TalkTimeException 如果条件不为假
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throwException(null, message);
        }
    }

    /**
     * 判断条件是否为假，如果不为假则抛出异常。
     *
     * @param expression 条件值
     * @param commonEnum 异常枚举
     * @param args       异常参数
     * @throws TalkTimeException 如果条件不为假
     */
    public static void isFalse(boolean expression, CommonEnum commonEnum, Object... args) {
        if (expression) {
            throwException(commonEnum, args);
        }
    }

    /**
     * 比较两个对象是否相等，如果不相等则抛出异常。
     *
     * @param source  源对象
     * @param target  目标对象
     * @param message 异常信息
     * @throws TalkTimeException 如果对象不相等
     */
    public static void equal(Object source, Object target, String message) {
        if (!ObjectUtil.equal(source, target)) {
            throwException(null, message);
        }
    }

    /**
     * 比较两个对象是否不相等，如果相等则抛出异常。
     *
     * @param source  源对象
     * @param target  目标对象
     * @param message 异常信息
     * @throws TalkTimeException 如果对象相等
     */
    public static void notEqual(Object source, Object target, String message) {
        if (ObjectUtil.equal(source, target)) {
            throwException(null, message);
        }
    }


    /**
     * 校验对象。
     *
     * @param object  待校验对象
     * @param isGroup 是否分组校验
     * @param groups  待校验的组
     * @throws TalkTimeException 如果校验不通过
     */
    public static void validateEntity(Object object, Boolean isGroup, Class<?>... groups) {
        Set<ConstraintViolation<Object>> validate;
        if (isGroup) {
            validate = SPRING_VALIDATOR_ADAPTER.validate(object, groups);
        } else {
            validate = SPRING_VALIDATOR_ADAPTER.validate(object);
        }
        Map<Path, String> errorMap = validate.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        if (CollUtil.isNotEmpty(errorMap)) {
            throw new TalkTimeException(ResultEnum.REQUEST_PARAM_ILLEGAL, errorMap);
        }
    }

    /**
     * 抛出异常。
     *
     * @param commonEnum 异常枚举
     * @param arg        异常参数
     * @throws TalkTimeException 异常
     */
    private static void throwException(CommonEnum commonEnum, Object... arg) {
        if (Objects.isNull(commonEnum)) {
            commonEnum = ResultEnum.REQUEST_ERROR;
        }
        throw new TalkTimeException(commonEnum.getCode(), MessageFormat.format(commonEnum.getMsg(), arg));
    }
}
