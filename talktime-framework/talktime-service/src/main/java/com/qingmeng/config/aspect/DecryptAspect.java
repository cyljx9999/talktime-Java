package com.qingmeng.config.aspect;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.alibaba.fastjson2.JSON;
import com.qingmeng.config.annotation.Decrypt;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.enums.system.ResultEnum;
import com.qingmeng.exception.TalkTimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 切面解密
 * @createTime 2023年04月16日 15:30:00
 */
@Aspect
@Component
@Slf4j
public class DecryptAspect {
    @Value("${rsa.encrypt.publicKey}")
    String publicKey;
    @Value("${rsa.encrypt.privateKey}")
    String privateKey;

    /**
     * 定义切点,使用了@Decrypt注解的类 或 使用了@Decrypt注解的方法
     */
    @Pointcut("@within(com.qingmeng.config.annotation.Decrypt) || @annotation(com.qingmeng.config.annotation.Decrypt)")
    public void pointcut(){}

    /**
     *  环绕切面
     */
    @Around("pointcut()")
    public CommonResult<?> around(ProceedingJoinPoint point){
        CommonResult<?>  result = null;
        //RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, publicKey);
        final ECIES ecies = new ECIES(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(),privateKey,publicKey);
        // 获取被代理方法参数
        Object[] args = point.getArgs();
        // 获取被代理对象
        Object target = point.getTarget();

        // 获取通知签名
        MethodSignature signature = (MethodSignature )point.getSignature();

        try {
            // 获取被代理方法
            Method pointMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
            // 获取被代理方法上面的注解@Decrypt
            Decrypt decrypt = pointMethod.getAnnotation(Decrypt.class);
            // 被代理方法上没有，则说明@Decrypt注解在被代理类上
            if(decrypt==null){
                decrypt = target.getClass().getAnnotation(Decrypt.class);
            }
            if(decrypt!=null){
                // 获取注解上声明的加解密类
                Class clazz = decrypt.value();
                // 获取注解上声明的加密参数名
                String encryptStrName = decrypt.encryptStrName();

                for (int i = 0; i < args.length; i++) {
                    // 如果是clazz类型则说明使用了加密字符串encryptStr传递的加密参数
                    if(clazz.isInstance(args[i])){
                        //将args[i]转换为clazz表示的类对象
                        Object cast = clazz.cast(args[i]);
                        // 通过反射，执行getEncryptStr()方法，获取加密数据
                        Method method = clazz.getMethod(getMethodName(encryptStrName));
                        // 执行方法，获取加密数据
                        String encryptStr = (String) method.invoke(cast);
                        // 加密字符串是否为空
                        if(StrUtil.isNotBlank(encryptStr)){
                            // 解密
                            log.info("执行解密操作");
                            String decryptByPrivate = UriUtils.decode(StrUtil.utf8Str(ecies.decrypt(encryptStr, KeyType.PrivateKey)), StandardCharsets.UTF_8);
                            log.info("解密后的参数:--->{}",decryptByPrivate);
                            // 转换vo
                            args[i] = JSON.parseObject(decryptByPrivate, (Type) args[i].getClass());
                        }else{
                            return CommonResult.error(ResultEnum.REQUEST_PARAM_ILLEGAL);
                        }
                    }
                    // 其他类型，比如基本数据类型、包装类型就不使用加密解密了
                }
            }
            result = (CommonResult<?>) point.proceed(args);
        } catch (Throwable throwable) {
            if (throwable instanceof TalkTimeException) {
                throw (TalkTimeException) throwable;
            }
            throwable.printStackTrace();
        }
        return result;
    }

    /**
     * 转化方法名
     */
    private String getMethodName(String name){
        String first = name.substring(0,1);
        String last = name.substring(1);
        first = StringUtils.upperCase(first);
        return "get" + first + last;
    }
    /**
     * 异常通知，发生异常走这里
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    public void doException(Throwable throwable) {
        log.error(String.valueOf(throwable));

    }


}
