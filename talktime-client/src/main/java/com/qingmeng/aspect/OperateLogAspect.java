package com.qingmeng.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.entity.SysOperateLog;
import com.qingmeng.enums.common.OperateEnum;
import com.qingmeng.event.SysOperateLogEvent;
import com.qingmeng.utils.CommonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:22:00
 */
@Aspect
@Component
public class OperateLogAspect {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 为了记录方法的执行时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 设置操作日志切入点，这里介绍两种方式：
     */
    @Pointcut("@annotation(com.qingmeng.annotation.SysLog)")
    public void operateLogPointCut() {
    }

    @Before("operateLogPointCut()")
    public void beforeMethod(){
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.qingmeng.controller..*.*(..))")
    public void operateExceptionLogPoinCut() {
    }


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param result      返回结果
     */
    @AfterReturning(value = "operateLogPointCut()", returning = "result")
    public void saveOperateLog(JoinPoint joinPoint, Object result) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            SysOperateLog sysOperateLog = buildSysOperateLogEntity(request,result,joinPoint,null,true);
            // 发送事件
            applicationEventPublisher.publishEvent(new SysOperateLogEvent(this,sysOperateLog,request));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     */
    @AfterThrowing(pointcut = "operateExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            SysOperateLog sysOperateLog = buildSysOperateLogEntity(request,null,joinPoint,e,false);
            // 发送事件
            applicationEventPublisher.publishEvent(new SysOperateLogEvent(this,sysOperateLog,request));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * 转换异常信息为字符串
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stet : elements) {
            stringBuilder.append(stet).append("\n");
        }
        String message = exceptionName + StrUtil.COLON + exceptionMessage + "\n\t" + stringBuilder;
        message = CommonUtils.substring(message,0 ,2000);
        return message;
    }



    private  SysOperateLog buildSysOperateLogEntity(HttpServletRequest request, Object result,JoinPoint joinPoint,Throwable e,boolean normal){
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        SysLog annotation = method.getAnnotation(SysLog.class);

        SysOperateLog sysOperateLog = new SysOperateLog();
        if (annotation != null) {
            //设置模块名称
            sysOperateLog.setTitle(annotation.title());
            //设置日志内容
            sysOperateLog.setContent(annotation.content());
        }
        // 将入参转换成json
        String params = CommonUtils.argsArrayToString(joinPoint.getArgs());
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName + "()";
        //设置请求方法
        sysOperateLog.setMethod(methodName);
        //设置请求方式
        assert request != null;
        sysOperateLog.setRequestMethod(request.getMethod());
        // 请求参数
        sysOperateLog.setRequestParam(params);
        // 获取用户名
        String name = "游客";
        if(StpUtil.isLogin()){
            name = StpUtil.getExtra("userName").toString();
        }
        sysOperateLog.setOperateName(name);
        // 请求URI
        sysOperateLog.setRequestUrl(request.getRequestURI());
        if (normal){
            // 返回结果
            sysOperateLog.setResponseResult(JSONUtil.toJsonStr(result));
            // 记录方法执行耗时时间（单位：毫秒）
            Long takeTime = System.currentTimeMillis() - startTime.get();
            sysOperateLog.setTakeTime(takeTime);
            // 操作状态（0正常 1异常）
            sysOperateLog.setStatus(OperateEnum.SUCCESS.getCode());
        }else {
            // 记录异常信息
            sysOperateLog.setErrorMsg(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            // 操作状态（0正常 1异常）
            sysOperateLog.setStatus(OperateEnum.ERROR.getCode());
        }
        startTime.remove();
        return sysOperateLog;
    }
}
