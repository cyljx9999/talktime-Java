package com.qingmeng.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 日志注解
 * @createTime 2023年11月10日 11:21:00
 */

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {
    /**
     * 模块标题
     */
    String title() default "";
    /**
     * 日志内容
     */
    String content() default "";

}
