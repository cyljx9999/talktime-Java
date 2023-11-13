package com.qingmeng.wxMp.service;

import com.qingmeng.adapt.TextBuilderAdapt;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;

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

    /**
     * 扫码关注后发送登录授权链接
     *
     * @param wxMpService service
     * @param wxMpXmlMessage 信息
     * @return {@link WxMpXmlOutMessage }
     * @author qingmeng
     * @createTime: 2023/11/13 11:29:00
     */
    public WxMpXmlOutMessage scan(WxMpService wxMpService, WxMpXmlMessage wxMpXmlMessage) {
        String skipUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        return new TextBuilderAdapt().build("请点击链接授权：<a href=\"" + skipUrl + "\">登录</a>", wxMpXmlMessage);
    }

}
