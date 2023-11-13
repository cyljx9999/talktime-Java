package com.qingmeng.adapt;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文本消息构建器类，用于构建回复文本消息。
 * @createTime 2023年11月13日 11:25:00
 */
public class TextBuilderAdapt {
    /**
     * 构建文本消息并返回。
     *
     * @param content    要回复的文本内容
     * @param wxMessage  接收到的微信消息对象
     * @return 构建的文本消息
     */
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage) {
        // 创建一个文本消息
        return WxMpXmlOutMessage.TEXT().content(content)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }
}
