package com.qingmeng.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author 清梦
 * @version 1.0.0
 * @Description 加密注解
 * @createTime 2023年04月16日 21:15:16
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {

}
