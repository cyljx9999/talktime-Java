package com.qingmeng.annotation;

import java.lang.annotation.*;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 防重复提交注解
 * @createTime 2023年11月09日 08:45:00
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {

    /**
     * 防重复操作限时标记数值（存储redis限时标记数值）
     */
    String value() default "noRepeatSubmit:" ;

    /**
     * 防重复操作过期时间（借助redis实现限时控制）
     */
    long expireSeconds() default 5;
}