package com.qingmeng.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qingmeng.enums.system.DesensitizationEnum;
import com.qingmeng.serialize.DesensitizationSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author 清梦
 * @version 1.0.0
 * @Description 脱敏注解
 * @createTime 2023年11月08日 15:12:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerialize.class)
public @interface Desensitization {

    /**
     * 数据脱敏类型
     */
    DesensitizationEnum type() default DesensitizationEnum.CUSTOM;

    /**
     * 脱敏开始位置（包含）
     */
    int start() default 0;

    /**
     * 脱敏结束位置（不包含）
     */
    int end() default 0;
}
