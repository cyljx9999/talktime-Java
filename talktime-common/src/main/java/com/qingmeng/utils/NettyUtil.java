package com.qingmeng.utils;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description nettyUtil工具类
 * @createTime 2023年11月21日 20:12:00
 */
public class NettyUtil {
    /**
     * 用于存储用户令牌的通道属性键
     */
    public static AttributeKey<String> TOKEN = AttributeKey.valueOf("token");

    /**
     * 用于存储用户IP地址的通道属性键
     */
    public static AttributeKey<String> IP = AttributeKey.valueOf("ip");

    /**
     * 用于存储用户UID（用户标识）的通道属性键
     */
    public static AttributeKey<Long> USERID = AttributeKey.valueOf("userId");

    /**
     * 用于存储 WebSocketServerHandshaker 的通道属性键，用于 WebSocket 握手时的处理
     */
    public static AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class, "HANDSHAKER");

    /**
     * 设置通道的属性值。
     *
     * @param channel       要设置属性的通道
     * @param attributeKey  属性键
     * @param data          要设置的属性值
     * @param <T>           属性值的类型
     */
    public static <T> void setAttr(Channel channel, AttributeKey<T> attributeKey, T data) {
        Attribute<T> attr = channel.attr(attributeKey);
        attr.set(data);
    }

    /**
     * 获取通道的属性值。
     *
     * @param channel       要获取属性的通道
     * @param attributeKey  属性键
     * @param <T>           属性值的类型
     * @return 通道的属性值，如果属性不存在则返回 null
     */
    public static <T> T getAttr(Channel channel, AttributeKey<T> attributeKey) {
        return channel.attr(attributeKey).get();
    }
}
