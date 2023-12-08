package com.qingmeng.config.netty.handler;

import cn.hutool.core.net.url.UrlBuilder;
import com.qingmeng.utils.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description HttpHeadersHandler 类是一个 Netty ChannelInboundHandler，用于处理 HTTP 请求头部信息。
 * *              它解析并提取请求中的 token 参数，并设置到 Netty 的 Channel 属性中，以及获取请求路径。
 * @createTime 2023年11月21日 20:11:00
 */
public class HttpHeadersHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当接收到通道的消息时，处理 HTTP 请求头部信息。
     *
     * @param ctx Netty 的 ChannelHandlerContext 对象
     * @param msg 接收到的消息对象
     * @throws Exception 如果处理时发生异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());

            // 获取token参数
            String token = Optional.ofNullable(urlBuilder.getQuery()).map(k -> k.get("token")).map(CharSequence::toString).orElse("");
            NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, token);

            // 获取请求路径
            request.setUri(urlBuilder.getPath().toString());
            HttpHeaders headers = request.headers();
            String ip = headers.get("X-Real-IP");
            // 如果没经过nginx，就直接获取远端地址
            if (StringUtils.isEmpty(ip)) {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            NettyUtil.setAttr(ctx.channel(), NettyUtil.IP, ip);
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(request);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}