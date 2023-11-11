package com.qingmeng.service.impl;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.qingmeng.adapt.LoginAboutAdapt;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.enums.user.LoginMethodEnum;
import com.qingmeng.exception.TalkTimeException;
import com.qingmeng.service.SysUserService;
import com.qingmeng.strategy.login.LoginFactory;
import com.qingmeng.strategy.login.LoginStrategy;
import com.qingmeng.utils.IdUtils;
import com.qingmeng.utils.RedisUtils;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:19:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    public static final String MATH = "math";
    public static final String CHAR = "char";
    @Resource
    private LoginFactory loginFactory;
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 验证码类型
     */
    private final String[] captchaArray = {MATH, CHAR};


    /**
     * 登陆
     *
     * @param paramDTO 登陆参数对象
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/11 00:49:54
     */
    @Override
    public TokenInfoVO login(LoginParamDTO paramDTO) {
        LoginMethodEnum typeEnum = LoginMethodEnum.get(paramDTO.getLoginMethod());
        LoginStrategy loginStrategy = loginFactory.getStrategyWithType(typeEnum.getValue());
        return loginStrategy.getTokenInfo(paramDTO);
    }

    /**
     * 获取验证码
     *
     * @return {@link CaptchaVO }
     * @author qingmeng
     * @createTime: 2023/11/11 14:27:50
     */
    @Override
    public CaptchaVO getCaptcha() {
        //生成随机四位数
        String uuid = IdUtils.simpleUUID();
        // 构造redis key
        String verifyKey = RedisConstant.CAPTCHA_CODE_KEY + uuid;
        // 随机获取本次生成的验证码类型
        String captchaType = captchaArray[new Random().nextInt(captchaArray.length)];
        String captchaStr;
        String code = null;
        //缓冲图像
        BufferedImage image = null;
        // 生成验证码
        if (MATH.equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            captchaStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(captchaStr);
        } else if (CHAR.equals(captchaType)) {
            captchaStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(captchaStr);
        }
        // redis缓存
        RedisUtils.set(verifyKey, code, RedisConstant.CAPTCHA_CODE_EXPIRE, TimeUnit.SECONDS);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new TalkTimeException("验证码生成失败");
        }
        //输出流转换为Base64
        String encode = Base64.encode(os.toByteArray());
        return LoginAboutAdapt.buildCaptchaVO(encode,uuid);
    }
}
