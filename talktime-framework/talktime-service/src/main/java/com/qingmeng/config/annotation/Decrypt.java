package com.qingmeng.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author 清梦
 * @version 1.0.0
 * @Description 解密注解
 * @createTime 2023年04月16日 21:15:16
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {

    // 参数类（用来传递加密数据,只有方法参数中有此类或此类的子类才会执行加解密）
    Class value();

    // 参数类中传递加密数据的属性名，默认encryptStr
    String encryptStrName() default "encryptStr";
}
