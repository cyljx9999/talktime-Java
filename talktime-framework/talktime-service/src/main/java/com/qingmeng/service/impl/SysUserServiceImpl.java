package com.qingmeng.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.google.code.kaptcha.Producer;
import com.qingmeng.config.adapt.LoginAboutAdapt;
import com.qingmeng.config.adapt.UserInfoAdapt;
import com.qingmeng.config.adapt.UserSettingAdapt;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.config.cache.UserFriendSettingCache;
import com.qingmeng.config.cache.UserSettingCache;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.*;
import com.qingmeng.dto.login.CheckFriendDTO;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.dto.user.AlterPersonalInfoDTO;
import com.qingmeng.dto.user.PersonalPrivacySettingDTO;
import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.enums.user.LoginMethodEnum;
import com.qingmeng.config.event.SysUserRegisterEvent;
import com.qingmeng.exception.TalkTimeException;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.service.SysUserService;
import com.qingmeng.config.strategy.login.LoginFactory;
import com.qingmeng.config.strategy.login.LoginStrategy;
import com.qingmeng.utils.*;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import com.qingmeng.vo.user.*;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Lazy
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
    @Resource
    private SysUserPrivacySettingDao sysUserPrivacySettingDao;
    @Resource
    private SysUserFriendDao sysUserFriendDao;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;
    @Resource
    private SysUserFriendSettingDao sysUserFriendSettingDao;
    @Resource
    private ChatRoomDao chatRoomDao;
    @Resource
    private ChatFriendRoomDao chatFriendRoomDao;
    @Resource
    private UserSettingCache userSettingCache;
    @Resource
    private SysUserFriendService sysUserFriendService;

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
        RedisUtils.set(verifyKey, code, RedisConstant.CAPTCHA_CODE_EXPIRE, TimeUnit.MINUTES);
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
        return LoginAboutAdapt.buildCaptchaVO(encode, uuid);
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
        AssertUtils.isTrue(RegexUtils.checkPhone(phone), "手机号格式错误");
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
        RedisUtils.set(key, String.valueOf(phoneCode), RedisConstant.PHONE_CODE_EXPIRE, TimeUnit.MINUTES);
        String code = "{\"code\":\"" + phoneCode + "\"}";
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
     * @param request  请求
     * @author qingmeng
     * @createTime: 2023/11/13 07:51:11
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO paramDTO, HttpServletRequest request) {
        // 密码加盐加密
        String encryptPassword = SaSecureUtil.md5BySalt(paramDTO.getPassword(), SystemConstant.MD5_SALT);
        paramDTO.setPassword(encryptPassword);
        SysUser sysUser = LoginAboutAdapt.buildRegister(paramDTO);
        registerCheck(paramDTO, sysUser);
        boolean save = sysUserDao.save(sysUser);
        if (save) {
            // 异步更新ip归属地
            applicationEventPublisher.publishEvent(new SysUserRegisterEvent(this, sysUser, request));
        }
        // 新增用户默认隐私设置表
        sysUserPrivacySettingDao.save(UserSettingAdapt.buildDefalutSysUserPrivacySetting(sysUser.getId()));
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
        return userCache.get(userId);
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
        return sysUserDao.getUserInfoByAccount(loginParamDTO);
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
     * 删除好友
     *
     * @param userId   用户 ID
     * @param friendId 好友 ID
     * @author qingmeng
     * @createTime: 2023/12/01 09:03:23
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFriend(Long userId, Long friendId) {
        SysUser sysUser = userCache.get(friendId);
        AssertUtils.isNull(sysUser, "无效的friendId");
        // 获取房间id
        String tagKey = CommonUtils.getKeyBySort(Arrays.asList(userId, friendId));
        ChatFriendRoom chatFriendRoom = chatFriendRoomDao.getInfoByKey(tagKey);
        // 删除抽象房间信息
        chatRoomDao.removeById(chatFriendRoom.getRoomId());
        // 删除好友设置
        userFriendSettingCache.delete(CommonUtils.getFriendSettingCacheKey(userId,friendId));
        sysUserFriendSettingDao.removeByTagKey(tagKey);
        // 删除好友
        sysUserFriendDao.removeByFriend(userId,friendId);
        userCache.evictFriendList(userId);
    }

    /**
     * 点击获取好友信息
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link ClickFriendInfoVo }
     * @author qingmeng
     * @createTime: 2023/12/02 09:52:10
     */
    @Override
    public ClickFriendInfoVo getFriendInfoByClick(Long userId, Long friendId) {
        // 检查是否为当前好友
        checkFriend(userId, friendId);
        // 获取我对当前好友设置的数据
        String cacheKey = CommonUtils.getFriendSettingCacheKey(userId, friendId);
        SysUserFriendSetting sysUserFriendSetting = userFriendSettingCache.get(cacheKey);
        // 获取好友信息
        SysUser sysUser = userCache.get(friendId);
        // todo 查询共同群聊
        return UserInfoAdapt.buildClickFriendInfoVo(sysUserFriendSetting,sysUser,new ArrayList<>());
    }

    /**
     * 获取个人隐私设置
     *
     * @param userId 用户 ID
     * @return {@link PersonalPrivacySettingVO }
     * @author qingmeng
     * @createTime: 2023/12/02 10:49:28
     */
    @Override
    public PersonalPrivacySettingVO getPersonalPrivacySetting(Long userId) {
        SysUserPrivacySetting setting = userSettingCache.get(userId);
        return UserSettingAdapt.buildPersonalPrivacySettingVO(setting);
    }

    /**
     * 更改个人隐私设置
     *
     * @param userId                    用户 ID
     * @param personalPrivacySettingDTO 个人隐私设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/02 11:31:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterPersonalPrivacySetting(Long userId, PersonalPrivacySettingDTO personalPrivacySettingDTO) {
        sysUserPrivacySettingDao.alterPersonalPrivacySetting(userId, personalPrivacySettingDTO);
        userSettingCache.delete(userId);
    }

    /**
     * 检查登录
     *
     * @return {@link CheckLoginVO }
     * @author qingmeng
     * @createTime: 2023/12/03 09:35:06
     */
    @Override
    public CheckLoginVO checkLogin() {
        return UserInfoAdapt.buildCheckLoginVO(StpUtil.isLogin());
    }

    /**
     * 更改账户
     *
     * @param userId          用户 ID
     * @param alterAccountDTO 更改账户 DTO
     * @author qingmeng
     * @createTime: 2023/11/23 21:45:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterAccount(Long userId, AlterAccountDTO alterAccountDTO) {
        SysUser sysUser = userCache.get(userId);
        AssertUtils.isTrue(sysUser.getAlterAccountCount() == 0, "帐户修改次数已用完");
        sysUserDao.alterAccount(userId,sysUser.getAlterAccountCount() - 1,alterAccountDTO.getUserAccount());
        userCache.delete(userId);
    }

    /**
     * 更改个人信息
     *
     * @param userId                      用户 ID
     * @param alterAccountPersonalInfoDTO 更改账户个人信息 DTO
     * @author qingmeng
     * @createTime: 2023/12/02 10:39:55
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterPersonalInfo(Long userId, AlterPersonalInfoDTO alterAccountPersonalInfoDTO) {
        sysUserDao.alterPersonalInfo(userId,alterAccountPersonalInfoDTO);
        userCache.delete(userId);
    }

    /**
     * 查看好友
     *
     * @param userId   用户 ID
     * @param friendId 好友 ID
     * @author qingmeng
     * @createTime: 2023/12/03 13:02:35
     */
    private void checkFriend(Long userId, Long friendId) {
        CheckFriendDTO friendDTO = new CheckFriendDTO();
        friendDTO.setFriendId(friendId);
        CheckFriendVO checkFriendVO = sysUserFriendService.checkFriend(userId, friendDTO);
        AssertUtils.isTrue(checkFriendVO.getCheckStatus(),"非法请求");
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
        SysUser userByAccount = sysUserDao.getUserInfoByAccount(sysUser.getUserAccount());
        AssertUtils.isNotNull(userByAccount, "账号重复");
        SysUser userByPhone = sysUserDao.getUserInfoByPhone(paramDTO.getPhone());
        AssertUtils.isNotNull(userByPhone, "手机号重复");
    }

}
