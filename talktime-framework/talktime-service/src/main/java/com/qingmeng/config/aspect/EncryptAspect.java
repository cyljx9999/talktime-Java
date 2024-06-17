package com.qingmeng.config.aspect;

import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.alibaba.fastjson2.JSON;
import com.qingmeng.config.annotation.Encrypt;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.enums.system.ResultEnum;
import com.qingmeng.exception.TalkTimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @author 清梦
 * @version 1.0.0
 * @Description 切面加密
 * @createTime 2023年04月16日 15:30:00
 */
@Aspect
@Component
@Slf4j
public class EncryptAspect {

    /**
     * 是否进行加密，通过配置文件注入（不配置默认为true）
     */
    @Value("${rsa.encrypt.open:true}")
    boolean open;
    @Value("${rsa.encrypt.otherPublicKey}")
    String publicKey;
    @Value("${rsa.encrypt.otherPrivateKey}")
    String privateKey;

    /**
     * 定义切点,使用了@Encrypt注解的类 或 使用了@Encrypt注解的方法
     */
    @Pointcut("@within(com.qingmeng.config.annotation.Encrypt) || @annotation(com.qingmeng.config.annotation.Encrypt)")
    public void pointcut(){}

    /**
     * 环绕切面
     */
    @Around("pointcut()")
    public CommonResult<?> around(ProceedingJoinPoint point){
        CommonResult<String>  result = new CommonResult<>();
        result.setCode(ResultEnum.REQUEST_SUCCESS.getCode());
        CommonResult<?>  result1 = null;
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
            // 获取被代理方法上面的注解@Encrypt
            Encrypt encrypt = pointMethod.getAnnotation(Encrypt.class);
            // 被代理方法上没有，则说明@Encrypt注解在被代理类上
            if(encrypt==null){
                encrypt = target.getClass().getAnnotation(Encrypt.class);
            }
            result1 = (CommonResult<?>) point.proceed(args);

            if (encrypt!=null){
                // 判断配置是否需要返回加密
                if(open){
                    // 获取返回值json字符串
                    String jsonString = JSON.toJSONString(result1.getData());
                    log.info("执行加密返回");
                    // 加密
                    //String encryptData = UriUtils.encode(StrUtil.utf8Str(jsonString), StandardCharsets.UTF_8);
                    String encryptBase64 = ecies.encryptBase64(jsonString, KeyType.PublicKey);
                    result.setData(encryptBase64);
                }
            }
        } catch (Throwable throwable) {
            if (throwable instanceof TalkTimeException) {
                throw (TalkTimeException) throwable;
            }
            throwable.printStackTrace();
        }
        return open ? result : result1;
    }


}
