package com.qingmeng.adapt;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.RandomUtil;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserAuth;
import com.qingmeng.enums.user.AccountStatusEnum;
import com.qingmeng.enums.user.SexEnum;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description token信息设适配类
 * @createTime 2023年11月10日 22:57:00
 */
public class LoginAboutAdapt {

    /**
     * 构造token信息类
     *
     * @param saTokenInfo satoken对象
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/10 22:59:23
     */
    public static TokenInfoVO buildTokenInfo(SaTokenInfo saTokenInfo){
        TokenInfoVO info = new TokenInfoVO();
        info.setTokenName(saTokenInfo.getTokenName());
        info.setTokenValue(saTokenInfo.getTokenValue());
        info.setTokenTimeout(saTokenInfo.getTokenTimeout());
        return info;
    }

    /**
     * 构造验证码返回类
     *
     * @param captcha 验证码base64字符串
     * @param uuid 验证码对应id
     * @return {@link CaptchaVO }
     * @author qingmeng
     * @createTime: 2023/11/11 14:45:28
     */
    public static CaptchaVO buildCaptchaVO(String captcha,String uuid){
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCode(captcha);
        captchaVO.setCodeId(uuid);
        return captchaVO;
    }

    /**
     * 构建注册对象
     *
     * @param param 注册账号参数
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/13 08:02:20
     */
    public static SysUser buildRegister(RegisterDTO param){
        SysUser sysUser = new SysUser();
        sysUser.setUserName(param.getUserName());
        sysUser.setUserAccount(param.getAccount());
        sysUser.setUserPassword(param.getPassword());
        sysUser.setUserPhone(param.getPhone());
        sysUser.setUserSex(SexEnum.UNKNOWN.getCode());
        sysUser.setAccountStatus(AccountStatusEnum.NORMAL.getCode());
        sysUser.setAlterAccountCount(1);
        sysUser.setLatitude(param.getLatitude());
        sysUser.setLongitude(param.getLongitude());
        return sysUser;
    }

    /**
     * 构造微信公众号的用户授权对象
     *
     * @param openId 第三方应用唯一凭证
     * @param userId 用户id
     * @return {@link SysUserAuth }
     * @author qingmeng
     * @createTime: 2023/11/13 15:10:13
     */
    public static SysUserAuth buildUserAuthWithMp(String openId, Long userId){
        SysUserAuth auth = new SysUserAuth();
        auth.setOpenId(openId);
        auth.setUserId(userId);
        auth.setLoginType("微信公众号");
        return auth;
    }

    /**
     * 构建默认注册对象
     *
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/13 08:02:20
     */
    public static SysUser buildDefaultRegister(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName(RandomUtil.randomString(8));
        sysUser.setUserAccount(RandomUtil.randomString(8));
        sysUser.setUserPassword(SaSecureUtil.md5BySalt(SystemConstant.DEFAULT_PASSWORD, SystemConstant.MD5_SALT));
        sysUser.setUserSex(SexEnum.UNKNOWN.getCode());
        sysUser.setAccountStatus(AccountStatusEnum.NORMAL.getCode());
        sysUser.setAlterAccountCount(1);
        return sysUser;
    }

    /**
     * 构建授权用户
     *
     * @param userId   用户 ID
     * @param userInfo 用户信息
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/20 08:48:09
     */
    public static SysUser buildAuthorizeUser(Long userId, WxOAuth2UserInfo userInfo) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setUserAvatar(userInfo.getHeadImgUrl());
        user.setUserName(userInfo.getNickname());
        user.setUserSex(userInfo.getSex());
        if (userInfo.getNickname().length() > 10) {
            user.setUserName(RandomUtil.randomString(8));
        } else {
            user.setUserName(userInfo.getNickname());
        }
        return user;
    }
}
