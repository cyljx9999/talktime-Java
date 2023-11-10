package com.qingmeng.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 分布式锁注解
 * @createTime 2023年11月08日 20:47:00
 */
@Retention(RetentionPolicy.RUNTIME)
// 作用在方法上
@Target(ElementType.METHOD)
public @interface RedissonLock {
    /**
     * 锁的键的前缀，默认取方法的全限定名，除非在不同方法上对同一个资源做分布式锁，才需要自己指定前缀。
     *
     * @return 锁的键的前缀
     */
    String prefixKey() default "";

    /**
     * 锁的键的表达式，使用 Spring EL 表达式来指定。
     *
     * @return 锁的键的表达式
     */
    String key();

    /**
     * 等待锁的时间，默认为 -1，表示不等待直接失败，与 Redisson 默认值一致。
     *
     * @return 等待锁的时间，单位为秒
     */
    int waitTime() default -1;

    /**
     * 等待锁的时间单位，默认为毫秒。
     *
     * @return 等待锁的时间单位
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
}

