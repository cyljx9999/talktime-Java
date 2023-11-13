package com.qingmeng.netty.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.qingmeng.netty.dto.WsBaseDTO;
import com.qingmeng.netty.enums.WSRequestTypeEnum;
import com.qingmeng.netty.service.WebSocketService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义handler 处理业务逻辑
 * @createTime 2023年11月13日 09:24:00
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private WebSocketService webSocketService;

    /**
     * 在处理程序添加到管道时，初始化 WebSocket 服务。
     *
     * @param ctx ChannelHandlerContext 上下文
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.webSocketService = getService();
    }

    /**
     * 处理用户事件触发的方法，如握手完成和读空闲。
     *
     * @param ctx ChannelHandlerContext对象
     * @param evt 事件对象
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // WebSocket 握手完成事件
            webSocketService.connect(ctx.channel());
        } else if (evt instanceof IdleStateEvent) {
            // 空闲状态事件
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // 读空闲事件，可以处理用户下线等逻辑
                log.info("读空闲");
                // todo: 用户下线处理，例如关闭通道
                ctx.channel().close();
            }
        }
    }

    /**
     * 处理接收到的 WebSocket 消息。
     *
     * @param channelHandlerContext ChannelHandlerContext对象
     * @param textWebSocketFrame 接收到的 TextWebSocketFrame 消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        // 获取前端传递的登陆类型数据并转化为对应的实体类对象
        WsBaseDTO wsBaseReq = JSONUtil.toBean(textWebSocketFrame.text(), WsBaseDTO.class);
        // 通过查询枚举获取具体的请求类型
        WSRequestTypeEnum wsReqTypeEnum = WSRequestTypeEnum.of(wsBaseReq.getType());
        // 匹配类型进行具体的操作
        switch (wsReqTypeEnum) {
            case LOGIN:
                // 登陆
                log.info("请求二维码 = " + textWebSocketFrame.text());
                break;
            case HEARTBEAT:
                // 心跳检测
                break;
            default:
                log.info("未知类型");
        }
    }


    /**
     * 获取 WebSocket 服务的实例。
     *
     * @return WebSocketService 实例
     */
    private WebSocketService getService(){
        return SpringUtil.getBean(WebSocketService.class);
    }

}

