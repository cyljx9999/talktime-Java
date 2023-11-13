package com.qingmeng.wxMp.config;

import com.qingmeng.wxMp.handler.LogHandler;
import com.qingmeng.wxMp.handler.MsgHandler;
import com.qingmeng.wxMp.handler.ScanHandler;
import com.qingmeng.wxMp.handler.SubscribeHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用于配置微信公众号（WxMp）相关的服务和消息路由的Spring Boot配置类
 * @createTime 2023年11月13日 11:05:00
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    /**
     * 日志处理
     */
    private final LogHandler logHandler;

    /**
     * 新消息处理
     */
    private final MsgHandler msgHandler;

    /**
     * 新关注处理
     */
    private final SubscribeHandler subscribeHandler;

    /**
     * 扫码处理
     */
    private final ScanHandler scanHandler;

    /**
     * 微信公众号配置
     */
    private final WxMpProperties properties;

    /**
     * 配置和提供用于处理微信公众号操作的WxMpService bean。
     *
     * @return 使用属性配置的WxMpService实例。
     * @throws RuntimeException 如果缺少配置。
     */
    @Bean
    public WxMpService wxMpService() {
        // 检查配置是否存在，如果不存在则抛出异常。
        final List<WxMpProperties.MpConfig> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("请确保阅读项目文档并配置必要的属性。");
        }

        // 创建WxMpService实例并设置配置。
        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(configs
                .stream().map(a -> {
                    WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
                    configStorage.setAppId(a.getAppId());
                    configStorage.setSecret(a.getSecret());
                    configStorage.setToken(a.getToken());
                    configStorage.setAesKey(a.getAesKey());
                    return configStorage;
                }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        return service;
    }

    /**
     * 配置和提供用于路由微信公众号消息的WxMpMessageRouter bean。
     *
     * @param wxMpService 用于消息路由的WxMpService。
     * @return 配置了消息处理规则的WxMpMessageRouter实例。
     */
    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 异步记录所有事件。
        newRouter.rule().handler(this.logHandler).next();

        // 处理关注事件。
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(this.subscribeHandler).end();

        // 处理扫码事件。
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();

        // 默认消息处理程序。
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}