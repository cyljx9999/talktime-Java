package com.qingmeng.netty.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.qingmeng.adapt.WsAdapter;
import com.qingmeng.netty.dto.WsChannelExtraDTO;
import com.qingmeng.netty.service.WebSocketService;
import com.qingmeng.netty.vo.WsBaseVO;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
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
    private static final Duration EXPIRE_TIME = Duration.ofHours(1);
    private static final Long MAX_MUM_SIZE = 10000L;

    /**
     * 所有请求登录的code与channel关系
     */
    public static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .expireAfterWrite(EXPIRE_TIME)
            .maximumSize(MAX_MUM_SIZE)
            .build();


    @Resource
    private WxMpService wxMpService;

    /**
     * 处理所有ws连接的事件
     *
     * @param channel 连接的通道
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WsChannelExtraDTO());
    }

    /**
     * 处理用户登录请求，需要返回一张带code的二维码
     *
     * @param channel 连接的通道
     */
    @SneakyThrows
    @Override
    public void getLoginQrcode(Channel channel) {
        //生成随机不重复的登录码,并将channel存在本地WAIT_LOGIN_MAP中
        Integer code = generateLoginCode(channel);
        //请求微信接口，获取登录码地址
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) EXPIRE_TIME.getSeconds());
        //返回给前端
        sendMsg(channel, WsAdapter.buildLoginQrcode(wxMpQrCodeTicket));
    }

    /**
     * 移除ws连接的事件
     *
     * @param channel 连接的通道
     */
    @Override
    public void removeConnect(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
    }

    /**
     * 生成随机不重复的登录码，并将 channel 存在本地 cache 中。
     *
     * @param channel 连接的通道
     * @return 生成的登录码
     */
    private Integer generateLoginCode(Channel channel) {
        int inc;
        do {
            inc = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (WAIT_LOGIN_MAP.asMap().containsKey(inc));
        //储存一份在本地
        WAIT_LOGIN_MAP.put(inc, channel);
        return inc;
    }

    /**
     * 向通道发送 WebSocket 消息。
     *
     * @param channel        连接的通道
     * @param wsBaseVO 要发送的 WebSocket 消息
     */
    private void sendMsg(Channel channel, WsBaseVO<?> wsBaseVO) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsBaseVO)));
    }
}
