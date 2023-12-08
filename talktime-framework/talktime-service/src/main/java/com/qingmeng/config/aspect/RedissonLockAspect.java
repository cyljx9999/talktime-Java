package com.qingmeng.config.aspect;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.config.annotation.RedissonLock;
import com.qingmeng.utils.LockUtils;
import com.qingmeng.utils.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 分布式锁切面类
 * @createTime 2023年11月08日 20:51:00
 */
@Slf4j
@Aspect
@Component
@Order(0)
public class RedissonLockAspect {
    @Resource
    private LockUtils lockUtils;

    /**
     * 环绕通知，用于处理带有 @RedissonLock 注解的方法。
     *
     * @param joinPoint 切点
     * @return 方法的执行结果
     * @throws Throwable 如果方法执行过程中抛出异常
     */
    @Around("@annotation(com.qingmeng.config.annotation.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        //默认方法限定名+注解排名（可能多个）
        String prefix = StrUtil.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockUtils.executeWithLockThrows(prefix + StrUtil.COLON + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
