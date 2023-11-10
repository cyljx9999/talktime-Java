package com.qingmeng.aspect;

import cn.hutool.json.JSONUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.entity.SysOperateLog;
import com.qingmeng.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:22:00
 */
@Aspect
@Component
public class OperateLogAspect {

    /**
     * 为了记录方法的执行时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 设置操作日志切入点，这里介绍两种方式：
     * 1、基于注解切入（也就是打了自定义注解的方法才会切入）
     *    @Pointcut("@annotation(org.wujiangbo.annotation.MyLog)")
     * 2、基于包扫描切入
     *    @Pointcut("execution(public * org.wujiangbo.controller..*.*(..))")
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
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            SysLog myLog = method.getAnnotation(SysLog.class);

            SysOperateLog sysOperateLog = new SysOperateLog();
            if (myLog != null) {
                //设置模块名称
                sysOperateLog.setTitle(myLog.title());
                //设置日志内容
                sysOperateLog.setContent(myLog.content());
            }
            // 将入参转换成json
            String params = argsArrayToString(joinPoint.getArgs());
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
            // 返回结果
            sysOperateLog.setResponseResult(JSONUtil.toJsonStr(result));
            // todo:获取用户名
            sysOperateLog.setOperateName("张三");
            // IP地址
            sysOperateLog.setIp(IpUtils.getIpAddr(request));
            // todo: IP归属地（真是环境中可以调用第三方API根据IP地址，查询归属地）
            sysOperateLog.setIpLocation("湖北武汉");
            // 请求URI
            sysOperateLog.setRequestUrl(request.getRequestURI());
            //操作状态（0正常 1异常）
            sysOperateLog.setStatus(0);
            //记录方法执行耗时时间（单位：毫秒）
            Long takeTime = System.currentTimeMillis() - startTime.get();
            sysOperateLog.setTakeTime(takeTime);
            // todo:异步插入数据库
            //operLogService.insert(sysOperateLog);
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
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysOperateLog sysOperateLog = new SysOperateLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName + "()";
            // 获取操作
            SysLog myLog = method.getAnnotation(SysLog.class);
            if (myLog != null) {
                //设置模块名称
                sysOperateLog.setTitle(myLog.title());
                //设置日志内容
                sysOperateLog.setContent(myLog.content());
            }
            // 将入参转换成json
            String params = argsArrayToString(joinPoint.getArgs());
            //设置请求方法
            sysOperateLog.setMethod(methodName);
            //设置请求方式
            assert request != null;
            sysOperateLog.setRequestMethod(request.getMethod());
            // 请求参数
            sysOperateLog.setRequestParam(params);
            // todo:获取用户名
            sysOperateLog.setOperateName("张三");
            // IP地址
            sysOperateLog.setIp(IpUtils.getIpAddr(request));
            // todo:IP归属地（真是环境中可以调用第三方API根据IP地址，查询归属地）
            sysOperateLog.setIpLocation("湖北武汉");
            // 请求URI
            sysOperateLog.setRequestUrl(request.getRequestURI());
            // 操作状态（0正常 1异常）
            sysOperateLog.setStatus(1);
            // 记录异常信息
            sysOperateLog.setErrorMsg(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            //todo:异步插入数据库
            //operLogService.insert(sysOperateLog);
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
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + stringBuilder.toString();
        message = substring(message,0 ,2000);
        return message;
    }
    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (o != null) {
                    try {
                        Object jsonObj = JSONUtil.toJsonStr(o);
                        params.append(jsonObj.toString()).append(" ");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.toString().trim();
    }


    /**
     * 字符串截取
     * @param str 字符串
     * @param start 起始位置
     * @param end 结束位置
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/10 11:48:47
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }
            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }
                return str.substring(start, end);
            }
        }
    }

    /**
     * 转换request 请求参数
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> returnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            returnMap.put(key, paramMap.get(key)[0]);
        }
        return returnMap;
    }

}
