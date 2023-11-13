package com.qingmeng.netty.service.impl;

import com.qingmeng.netty.dto.WsChannelExtraDTO;
import com.qingmeng.netty.service.WebSocketService;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 处理websocket所有逻辑
 * @createTime 2023年11月13日 11:34:00
 */
@Service
public class WebsocketServiceImpl implements WebSocketService {
    /**
     * 所有已连接的websocket连接列表和一些额外参数
     */
    private static final ConcurrentHashMap<Channel, WsChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    /**
     * 处理所有ws连接的事件
     *
     * @param channel 连接的通道
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WsChannelExtraDTO());
    }
}
