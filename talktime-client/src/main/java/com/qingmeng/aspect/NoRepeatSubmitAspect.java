package com.qingmeng.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.qingmeng.annotation.NoRepeatSubmit;
import com.qingmeng.enums.ResultEnum;
import com.qingmeng.exception.TalkTimeException;
import com.qingmeng.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月09日 08:46:00
 */
@Slf4j
@Component
@Aspect
public class NoRepeatSubmitAspect  {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.qingmeng.annotation.NoRepeatSubmit)")
    public void preventDuplication() {}

    @Around("preventDuplication()")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 获取执行方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        //获取防重复提交注解
        NoRepeatSubmit annotation = method.getAnnotation(NoRepeatSubmit.class);

        // 获取token以及方法标记，生成redisKey和redisValue
        String token = StpUtil.getTokenValue();

        String url = request.getRequestURI();

        // 通过前缀 + url + token + 函数参数签名 来生成redis上的 key
        String redisKey = url.concat(token).concat(getMethodSign(method, joinPoint.getArgs()));

        // 这个值只是为了标记，不重要
        String redisValue = redisKey.concat(annotation.value()).concat("submit duplication");

        if (!RedisUtils.hasKey(redisKey)) {
            // 设置防重复操作限时标记（前置通知）
            RedisUtils.set(redisKey, redisValue, annotation.expireSeconds(), TimeUnit.SECONDS);
            try {
                //正常执行方法并返回
                //ProceedingJoinPoint类型参数可以决定是否执行目标方法，
                // 且环绕通知必须要有返回值，返回值即为目标方法的返回值
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                //确保方法执行异常实时释放限时标记(异常后置通知)
                RedisUtils.delete(redisKey);
                if (throwable instanceof TalkTimeException) {
                    throw (TalkTimeException) throwable;
                }
                log.info("方法执行异常：{}", throwable.getMessage());
            }
        } else {
            // 重复提交了抛出异常，如果是在项目中，根据具体情况处理。
            throw new TalkTimeException(ResultEnum.REQUEST_REPEAT);
        }
        return null;
    }

    /**
     * 生成方法标记：采用数字签名算法SHA1对方法签名字符串加签
     *
     * @param method 方法
     * @param args 参数
     */
    private String getMethodSign(Method method, Object... args) {
        StringBuilder sb = new StringBuilder(method.toString());
        for (Object arg : args) {
            sb.append(toString(arg));
        }
        return DigestUtil.sha1Hex(sb.toString());
    }

    private String toString(Object arg) {
        if (Objects.isNull(arg)) {
            return "null";
        }
        if (arg instanceof Number) {
            return arg.toString();
        }
        return JSONUtil.toJsonStr(arg);
    }

}