package com.qingmeng.netty.config;

import com.qingmeng.constant.SystemConstant;
import com.qingmeng.netty.handler.NettyWebSocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 配置netty连接websocket
 * @createTime 2023年11月13日 09:20:00
 */
@Configuration
@Slf4j
public class NettyWebSocketServerConfig {

    /**
     * WebSocket 服务器的处理器
     */
    public static final NettyWebSocketServerHandler NETTY_WEB_SOCKET_SERVER_HANDLER = new NettyWebSocketServerHandler();

    /**
     * 创建线程池执行器，用于接受客户端连接
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    /**
     * 创建工作线程池执行器，用于处理客户端连接
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());

    /**
     * 启动 WebSocket 服务器
     */
    @PostConstruct
    public void start() throws InterruptedException {
        run();
    }

    /**
     * 销毁服务器资源
     */
    @PreDestroy
    public void destroy() {
        // 优雅地关闭 bossGroup
        Future<?> future = bossGroup.shutdownGracefully();
        // 优雅地关闭 workerGroup
        Future<?> future1 = workerGroup.shutdownGracefully();
        // 等待关闭完成
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("关闭 WebSocket 服务器成功");
    }

    public void run() throws InterruptedException {
        // 创建服务器启动引导对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 配置 bossGroup 和 workerGroup
        serverBootstrap.group(bossGroup, workerGroup)
                // 指定使用 NIO 传输
                .channel(NioServerSocketChannel.class)
                // 设置队列大小
                .option(ChannelOption.SO_BACKLOG, 128)
                // 保持连接状态
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 为 bossGroup 添加日志处理器，用于记录服务器启动过程中的日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 获取 Channel 的处理管道
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 30秒客户端没有向服务器发送心跳则关闭连接
                        //pipeline.addLast(new IdleStateHandler(30, 0, 0));
                        // 因为使用 http 协议，所以需要使用 http 的编码器和解码器
                        pipeline.addLast(new HttpServerCodec());
                        // 以块方式写，添加 ChunkedWriteHandler 处理器
                        pipeline.addLast(new ChunkedWriteHandler());
                        /*
                         * 说明：
                         *  1. http 数据在传输过程中是分段的，HttpObjectAggregator 可以把多个段聚合起来；
                         *  2. 这就是为什么当浏览器发送大量数据时，会发出多次 http 请求的原因
                         */
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        // 保存用户 IP 地址
                        // pipeline.addLast(new HttpHeadersHandler());
                        /*
                         * 说明：
                         *  1. 对于 WebSocket，它的数据是以帧 frame 的形式传递的；
                         *  2. 可以看到 WebSocketFrame 下面有 6 个子类
                         *  3. 浏览器发送请求时：ws://localhost:7000/hello 表示请求的 URI
                         *  4. WebSocketServerProtocolHandler 核心功能是把 http 协议升级为 ws 协议，保持长连接；
                         *     是通过一个状态码 101 来切换的
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                        // 自定义 handler ，处理业务逻辑
                        pipeline.addLast(NETTY_WEB_SOCKET_SERVER_HANDLER);
                    }
                });
        serverBootstrap.bind(SystemConstant.WEB_SOCKET_PORT).sync();
    }
}
