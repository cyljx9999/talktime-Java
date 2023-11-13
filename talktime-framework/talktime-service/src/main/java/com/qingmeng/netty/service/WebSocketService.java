package com.qingmeng.netty.service;

import io.netty.channel.Channel;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 处理websocket所有逻辑
 * @createTime 2023年11月13日 11:33:00
 */
public interface WebSocketService {

    /**
     * 处理所有ws连接的事件
     * @param channel 连接的通道
     */
    void connect(Channel channel);
}
