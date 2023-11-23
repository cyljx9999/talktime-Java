package com.qingmeng.netty.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.qingmeng.dto.login.WsAuthorizeDTO;
import com.qingmeng.netty.dto.WsBaseDTO;
import com.qingmeng.netty.enums.WSRequestTypeEnum;
import com.qingmeng.netty.service.WebSocketService;
import com.qingmeng.utils.NettyUtil;
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
     * 客户端主动关闭事件
     * @param ctx ChannelHandlerContext对象
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        userOffLine(ctx);
    }

    /**
     * 客户端离线
     *
     * @param ctx ChannelHandlerContext对象
     * @author qingmeng
     * @createTime: 2023/11/22 23:12:08
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        userOffLine(ctx);
    }

    /**
     * 捕获异常
     *
     * @param ctx   ChannelHandlerContext对象
     * @param cause 原因
     * @author qingmeng
     * @createTime: 2023/11/22 23:13:03
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("异常发生，异常消息 ={}", cause.getMessage());
        ctx.channel().close();
    }

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
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if (StrUtil.isNotBlank(token)) {
                this.webSocketService.authorize(ctx.channel(), new WsAuthorizeDTO(token));
            }
        } else if (evt instanceof IdleStateEvent) {
            // 空闲状态事件
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                userOffLine(ctx);
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
        WsBaseDTO wsBaseDTO = JSONUtil.toBean(textWebSocketFrame.text(), WsBaseDTO.class);
        // 通过查询枚举获取具体的请求类型
        WSRequestTypeEnum wsReqTypeEnum = WSRequestTypeEnum.of(wsBaseDTO.getType());
        // 匹配类型进行具体的操作
        switch (wsReqTypeEnum) {
            case QR_CODE:
                // 登陆
                webSocketService.getLoginQrcode(channelHandlerContext.channel());
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

    /**
     * 用户下线
     * @param ctx ChannelHandlerContext对象
     */
    private void userOffLine(ChannelHandlerContext ctx) {
        this.webSocketService.removeConnect(ctx.channel());
        ctx.channel().close();
    }
}

