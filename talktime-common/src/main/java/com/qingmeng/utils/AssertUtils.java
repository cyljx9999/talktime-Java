package com.qingmeng.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.qingmeng.enums.system.CommonEnum;
import com.qingmeng.enums.system.ResultEnum;
import com.qingmeng.exception.TalkTimeException;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
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
public class AssertUtils {

    /**
     * SpringValidatorAdapter 实例。
     */
    public static final SpringValidatorAdapter SPRING_VALIDATOR_ADAPTER = SpringUtils.getBean(SpringValidatorAdapter.class);


    /**
     * 为 null
     *
     * @param object  对象
     * @param message 消息
     * @author qingmeng
     * @createTime: 2023/11/21 23:04:01
     */
    public static void isNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throwException(null, message);
        }
    }

    /**
     * 不为 null
     *
     * @param object  对象
     * @param message 消息
     * @author qingmeng
     * @createTime: 2023/11/21 23:04:18
     */
    public static void isNotNull(Object object, String message) {
        if (Objects.nonNull(object)) {
            throwException(null, message);
        }
    }


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
     * @throws TalkTimeException 如果条件不为真
     */
    public static void isTrue(boolean expression, CommonEnum commonEnum) {
        if (!expression) {
            throwException(commonEnum, "");
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
     * @throws TalkTimeException 如果条件不为假
     */
    public static void isFalse(boolean expression, CommonEnum commonEnum) {
        if (expression) {
            throwException(commonEnum, "");
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
     * 不为空
     *
     * @param object 对象
     * @param msg    信息
     * @author qingmeng
     * @createTime: 2023/12/01 16:39:32
     */
    public static void isNotEmpty(Object object, String msg) {
        if (ObjectUtil.isEmpty(object)) {
            throwException(null,msg);
        }
    }

    /**
     * 检查小于
     *
     * @param size   大小
     * @param minNum 最小数量
     * @param msg    信息
     * @author qingmeng
     * @createTime: 2023/12/08 10:50:59
     */
    public static void checkLessThan(int size,int minNum, String msg){
        if (size < minNum) {
            throwException(null,msg);
        }
    }

    /**
     * 检查大于
     *
     * @param size   大小
     * @param maxNum 最大数量
     * @param msg    信息
     * @author qingmeng
     * @createTime: 2023/12/08 11:28:56
     */
    public static void checkGreaterThan(Long size,Long maxNum, String msg){
        if (size > maxNum) {
            throwException(null,msg);
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
     * @param message    消息
     * @author qingmeng
     * @createTime: 2024/06/19 16:46:57
     */
    private static void throwException(CommonEnum commonEnum, String message) {
        if (Objects.isNull(commonEnum)) {
            commonEnum = ResultEnum.REQUEST_ERROR;
        }else {
            message = commonEnum.getMsg();
        }
        throw new TalkTimeException(commonEnum.getCode(), message);
    }
}
