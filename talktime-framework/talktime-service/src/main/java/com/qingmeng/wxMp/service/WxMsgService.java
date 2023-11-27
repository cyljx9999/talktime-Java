package com.qingmeng.wxMp.service;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.adapt.LoginAboutAdapt;
import com.qingmeng.adapt.TextBuilderAdapt;
import com.qingmeng.adapt.UserSettingAdapt;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserAuth;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.netty.service.WebSocketService;
import com.qingmeng.service.SysUserAuthService;
import com.qingmeng.service.SysUserPrivacySettingService;
import com.qingmeng.service.SysUserService;
import com.qingmeng.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 与微信公众号api交互
 * @createTime 2023年11月13日 11:27:00
 */
@Service
@Slf4j
public class WxMsgService {
    /**
     * 用户的openId和前端登录场景code的映射关系
     */
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    @Value("${wx.mp.callback}")
    private String callback;

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserAuthService sysUserAuthService;
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private SysUserPrivacySettingService sysUserPrivacySettingService;

    /**
     * 扫码关注后发送登录授权链接
     *
     * @param wxMpService    service
     * @param wxMpXmlMessage 信息
     * @return {@link WxMpXmlOutMessage }
     * @author qingmeng
     * @createTime: 2023/11/13 11:29:00
     */
    @Transactional(rollbackFor = Exception.class)
    public WxMpXmlOutMessage scan(WxMpService wxMpService, WxMpXmlMessage wxMpXmlMessage) {
        // 获取用户的openId
        String openId = wxMpXmlMessage.getFromUser();
        // 获取前端登录场景code
        Integer loginCode = Integer.parseInt(getEventKey(wxMpXmlMessage));
        // 查询用户授权信息
        SysUserAuth userAuth = sysUserAuthService.getAuthInfoWithOpenId(openId);
        if (Objects.nonNull(userAuth)) {
            SysUser user = sysUserService.getUserInfoWithId(userAuth.getUserId());
            /*
             * 如果已经注册,直接登录成功
             * StringUtils.isNotEmpty(user.getAvatar()) 判断这个的目的是为了确保用户点击授权链接进行授权
             */
            if (Objects.nonNull(user) && StrUtil.isNotEmpty(user.getUserAvatar())) {
                webSocketService.scanLoginSuccess(loginCode, user.getId());
                return null;
            }
        }
        // 未注册用户自动注册账号
        SysUser saveUser = LoginAboutAdapt.buildDefaultRegister();
        boolean saveFlag = sysUserService.save(saveUser);
        if (saveFlag) {
            // 保存第三方授权信息
            SysUserAuth saveUserAuth = LoginAboutAdapt.buildUserAuthWithMp(openId, saveUser.getId());
            sysUserAuthService.save(saveUserAuth);
            // 新增用户隐私设置
            SysUserPrivacySetting saveSysUserPrivacySetting = UserSettingAdapt.buildDefalutSysUserPrivacySetting(saveUser.getId());
            sysUserPrivacySettingService.save(saveSysUserPrivacySetting);
        }
        //在redis中保存openid和场景code的关系，后续才能通知到前端,旧版数据没有清除,这里设置了过期时间
        RedisUtils.set(RedisConstant.OPEN_ID_CODE, String.valueOf(loginCode), RedisConstant.OPEN_ID_CODE_EXPIRE, TimeUnit.MINUTES);
        // 授权流程,给用户发送授权消息，并且异步通知前端扫码成功,等待授权
        webSocketService.scanSuccess(loginCode);
        String skipUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        WxMpXmlOutMessage.TEXT().build();
        return new TextBuilderAdapt().build("请点击链接授权：<a href=\"" + skipUrl + "\">登录</a>", wxMpXmlMessage);
    }

    /**
     * 获取事件关键字
     *
     * @param wxMpXmlMessage 微信公众号消息
     * @return 返回事件关键字
     */
    private String getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        // 扫码关注的渠道事件有前缀，需要去除
        return wxMpXmlMessage.getEventKey().replace("qrscene_", "");
    }

    /**
     * 授权
     *
     * @param userInfo 用户信息
     * @author qingmeng
     * @createTime: 2023/11/20 08:46:11
     */
    public void authorize(WxOAuth2UserInfo userInfo) {
        // 查询用户授权信息
        SysUserAuth userAuth = sysUserAuthService.getAuthInfoWithOpenId(userInfo.getOpenid());
        // 使用UserAdapter构建要更新的用户信息
        SysUser update = LoginAboutAdapt.buildAuthorizeUser(userAuth.getUserId(), userInfo);
        if (StrUtil.isBlank(update.getUserAvatar())) {
            sysUserService.updateWithId(update);
        }
        Integer code = Integer.parseInt(RedisUtils.get(RedisConstant.OPEN_ID_CODE));
        webSocketService.scanLoginSuccess(code, userAuth.getUserId());
    }
}
