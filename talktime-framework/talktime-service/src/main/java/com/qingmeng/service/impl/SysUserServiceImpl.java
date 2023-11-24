package com.qingmeng.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.google.code.kaptcha.Producer;
import com.qingmeng.adapt.LoginAboutAdapt;
import com.qingmeng.adapt.UserInfoAdapt;
import com.qingmeng.cache.UserCache;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.user.LoginMethodEnum;
import com.qingmeng.event.SysUserRegisterEvent;
import com.qingmeng.exception.TalkTimeException;
import com.qingmeng.service.SysUserService;
import com.qingmeng.strategy.login.LoginFactory;
import com.qingmeng.strategy.login.LoginStrategy;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.utils.IdUtils;
import com.qingmeng.utils.RedisUtils;
import com.qingmeng.utils.RegexUtils;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import com.qingmeng.vo.user.PersonalInfoVO;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
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
    @Value("${alibaba.sms.accessKeyId}")
    private String aliAccessKeyId;
    @Value("${alibaba.sms.accessKeySecret}")
    private String aliAccessKeySecret;

    @Resource
    private LoginFactory loginFactory;
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private UserCache userCache;

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
        String encode = "data:image/jpeg;base64," + Base64.encode(os.toByteArray());
        return LoginAboutAdapt.buildCaptchaVO(encode,uuid);
    }

    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     * @author qingmeng
     * @createTime: 2023/11/11 21:14:04
     */
    @Override
    public void sendPhone(String phone) {
        AsserUtils.isTrue(RegexUtils.checkPhone(phone),"手机号格式错误");
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(aliAccessKeyId)
                .accessKeySecret(aliAccessKeySecret)
                .build());

        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();
        Integer phoneCode = Integer.parseInt(RandomUtil.randomNumbers(4));
        String key = RedisConstant.PHONE_CODE_KEY + phone;
        RedisUtils.set(key, String.valueOf(phoneCode),RedisConstant.PHONE_CODE_EXPIRE, TimeUnit.MINUTES);
        String code = "{\"code\":\""+phoneCode+"\"}";
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("阿里云短信测试")
                .templateCode("SMS_154950909")
                .phoneNumbers(phone)
                .templateParam(code)
                .build();
        client.sendSms(sendSmsRequest);
    }

    /**
     * 注册
     *
     * @param paramDTO 参数对象
     * @param request 请求
     * @author qingmeng
     * @createTime: 2023/11/13 07:51:11
     */
    @Override
    public void register(RegisterDTO paramDTO, HttpServletRequest request) {
        // 密码加盐加密
        String encryptPassword = SaSecureUtil.md5BySalt(paramDTO.getPassword(), SystemConstant.MD5_SALT);
        paramDTO.setPassword(encryptPassword);
        SysUser sysUser = LoginAboutAdapt.buildRegister(paramDTO);
        registerCheck(paramDTO, sysUser);
        boolean save = sysUserDao.save(sysUser);
        if (save){
            /*
            获取ip归属地比较耗时，所以采用异步更新
            更新采用根据主键更新，需要更新插入成功再发布事件
             */
            applicationEventPublisher.publishEvent(new SysUserRegisterEvent(this,sysUser,request));
        }
    }


    /**
     * 使用 ID 获取用户信息
     *
     * @param userId 用户 ID
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/20 08:39:08
     */
    @Override
    public SysUser getUserInfoWithId(Long userId) {
        return userCache.getUserInfoById(userId);
    }

    /**
     * 保存
     *
     * @param saveUser 用户
     * @return boolean
     * @author qingmeng
     * @createTime: 2023/11/20 08:41:14
     */
    @Override
    public boolean save(SysUser saveUser) {
        return sysUserDao.save(saveUser);
    }

    /**
     * 使用 ID 更新
     *
     * @param update 更新
     * @author qingmeng
     * @createTime: 2023/11/20 08:53:22
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithId(SysUser update) {
        sysUserDao.updateById(update);
        userCache.delete(update.getId());
    }

    /**
     * 按帐户获取用户信息
     *
     * @param loginParamDTO login param dto
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 07:42:34
     */
    @Override
    public SysUser getUserInfoByAccount(LoginParamDTO loginParamDTO) {
        return sysUserDao.getUserInfoByAccountAndPassword(loginParamDTO);
    }

    /**
     * 根据ids查询用户集合
     *
     * @param userIds 用户 ID
     * @return {@link List }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/22 09:28:46
     */
    @Override
    public List<SysUser> listByIds(List<Long> userIds) {
        return userCache.userListByIds(userIds);
    }

    /**
     * 获取个人信息
     *
     * @param userId 用户 ID
     * @return {@link PersonalInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/23 21:55:38
     */
    @Override
    public PersonalInfoVO getPersonalInfo(Long userId) {
        SysUser sysUser = userCache.get(userId);
        return UserInfoAdapt.buildPersonalInfoVO(sysUser);
    }

    /**
     * 佩戴头像边框
     *
     * @param userId    用户 ID
     * @param articleId 物品 ID
     * @author qingmeng
     * @createTime: 2023/11/24 22:19:16
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wearArticle(Long userId, Long articleId) {
        sysUserDao.wearArticle(userId,articleId);
        userCache.delete(userId);
    }

    /**
     * 更改帐户
     *
     * @param userId          用户 ID
     * @param alterAccountDTO 更改帐户 DTO
     * @author qingmeng
     * @createTime: 2023/11/23 21:45:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterAccount(Long userId, AlterAccountDTO alterAccountDTO) {
        SysUser sysUser = userCache.get(userId);
        AsserUtils.isTrue(sysUser.getAlterAccountCount() == 0, "帐户修改次数已用完");
        sysUserDao.alterAccount(userId);
        userCache.delete(userId);
    }

    /**
     * 注册检查
     *
     * @param paramDTO 参数 dto
     * @param sysUser  sys 用户
     * @author qingmeng
     * @createTime: 2023/11/22 07:58:31
     */
    private void registerCheck(RegisterDTO paramDTO, SysUser sysUser) {
        SysUser userByAccount = sysUserDao.getUserInfoByAccountAndPassword(sysUser.getUserAccount());
        AsserUtils.isNotNull(userByAccount,"账号重复");
        SysUser userByPhone = sysUserDao.getUserInfoByPhone(paramDTO.getPhone());
        AsserUtils.isNotNull(userByPhone,"手机号重复");
    }

}
