package com.qingmeng.config.wxMp.handler;

import com.qingmeng.config.wxMp.service.WxMsgService;
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
 * @Description 扫码处理
 * @createTime 2023年11月13日 11:10:00
 */
@Component
public class ScanHandler implements WxMpMessageHandler {

    @Resource
    private WxMsgService wxMsgService;

    /**
     * 处理扫码事件的方法。
     *
     * @param wxMpXmlMessage  微信公众号收到的消息
     * @param map             扩展参数映射
     * @param wxMpService     微信公众号服务
     * @param wxSessionManager 微信会话管理器
     * @return 处理后的回复消息
     * @throws WxErrorException 如果处理过程中发生微信错误异常
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        return wxMsgService.scan(wxMpService, wxMpXmlMessage);
    }
}
