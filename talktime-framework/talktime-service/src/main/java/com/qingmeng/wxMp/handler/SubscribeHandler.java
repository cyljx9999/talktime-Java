package com.qingmeng.wxMp.handler;

import com.qingmeng.adapt.TextBuilderAdapt;
import com.qingmeng.wxMp.service.WxMsgService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 新关注处理
 * @createTime 2023年11月13日 11:11:00
 */
@Slf4j
@Component
public class SubscribeHandler implements WxMpMessageHandler {
    @Resource
    private WxMsgService wxMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(weixinService, wxMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilderAdapt().build("感谢关注", wxMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpService weixinService, WxMpXmlMessage wxMessage) {
        return wxMsgService.scan(weixinService, wxMessage);
    }
}
