package com.qingmeng.wxMp.handler;

import cn.hutool.json.JSONUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息处理
 * @createTime 2023年11月13日 11:08:00
 */
@Component
public class MsgHandler implements WxMpMessageHandler {
    //@Autowired
    //private WxMsgDao wxMsgDao;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        if (true) {
            //WxMsg msg = new WxMsg();
            //msg.setOpenId(wxMessage.getFromUser());
            //msg.setMsg(wxMessage.getContent());
            //wxMsgDao.save(msg);
            return null;
        }
        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //组装回复消息
        String content = "收到信息内容：" + JSONUtil.toJsonStr(wxMessage);

        //return new TextBuilder().build(content, wxMessage, weixinService);
        return null;
    }
}
