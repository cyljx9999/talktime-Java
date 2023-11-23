package com.qingmeng.netty.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.qingmeng.adapt.WsAdapter;
import com.qingmeng.cache.UserCache;
import com.qingmeng.dto.login.WsAuthorizeDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.user.LoginDeviceEnum;
import com.qingmeng.event.UserOfflineEvent;
import com.qingmeng.event.UserOnlineEvent;
import com.qingmeng.netty.dto.WsChannelExtraDTO;
import com.qingmeng.netty.service.WebSocketService;
import com.qingmeng.netty.vo.WsBaseVO;
import com.qingmeng.service.SysUserService;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.utils.IpUtils;
import com.qingmeng.utils.NettyUtil;
import com.qingmeng.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 处理websocket所有逻辑
 * @createTime 2023年11月13日 11:34:00
 */
@Service
public class WebsocketServiceImpl implements WebSocketService {

    @Value("${sa-token.token-name}")
    private String tokenName;
    @Resource
    private SysUserService sysUserService;

    /**
     * 所有已连接的websocket连接列表和一些额外参数
     */
    private static final ConcurrentHashMap<Channel, WsChannelExtraDTO> CONNECT_WS_MAP = new ConcurrentHashMap<>();
    private static final Duration EXPIRE_TIME = Duration.ofHours(1);
    private static final Long MAX_MUM_SIZE = 10000L;

    /**
     * 所有请求登录的code与channel关系
     */
    public static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .expireAfterWrite(EXPIRE_TIME)
            .maximumSize(MAX_MUM_SIZE)
            .build();

    /**
     * 所有在线的用户和对应的socket
     */
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Channel>> ONLINE_USERID_MAP = new ConcurrentHashMap<>();


    @Resource
    @Lazy
    private WxMpService wxMpService;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    @Qualifier("visibleTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private UserCache userCache;

    /**
     * 处理所有ws连接的事件
     *
     * @param channel 连接的通道
     */
    @Override
    public void connect(Channel channel) {
        CONNECT_WS_MAP.put(channel, new WsChannelExtraDTO());
    }

    /**
     * 处理用户登录请求，需要返回一张带code的二维码
     *
     * @param channel 连接的通道
     */
    @SneakyThrows
    @Override
    public void getLoginQrcode(Channel channel) {
        // 生成随机不重复的登录码,并将channel存在本地WAIT_LOGIN_MAP中
        Integer code = generateLoginCode(channel);
        // 请求微信接口，获取登录码地址
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) EXPIRE_TIME.getSeconds());
        // 返回给前端
        sendMsg(channel, WsAdapter.buildLoginQrcode(wxMpQrCodeTicket));
    }

    /**
     * 移除ws连接的事件
     *
     * @param channel 连接的通道
     */
    @Override
    public void removeConnect(Channel channel) {
        WsChannelExtraDTO wsChannelExtraDTO = CONNECT_WS_MAP.get(channel);
        Long userId = wsChannelExtraDTO.getUserId();
        if (Objects.nonNull(userId)) {
            CopyOnWriteArrayList<Channel> channels = ONLINE_USERID_MAP.get(userId);
            if (CollectionUtil.isNotEmpty(channels)) {
                channels.removeIf(ch -> Objects.equals(ch, channel));
            }
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            sysUser.setLastOperateTime(new Date());
            applicationEventPublisher.publishEvent(new UserOfflineEvent(this, sysUser));
        }
        CONNECT_WS_MAP.remove(channel);

    }

    /**
     * 扫描成功
     *
     * @param loginCode 登录代码
     * @author qingmeng
     * @createTime: 2023/11/20 09:57:07
     */
    @Override
    public void scanSuccess(Integer loginCode) {
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(loginCode);
        if (Objects.nonNull(channel)) {
            sendMsg(channel, WsAdapter.buildScanSuccessVO());
        }
    }

    /**
     * 授权
     *
     * @param channel      渠道
     * @param authorizeDTO 授权 DTO
     * @author qingmeng
     * @createTime: 2023/11/21 19:35:25
     */
    @Override
    public void authorize(Channel channel, WsAuthorizeDTO authorizeDTO) {
        String token = authorizeDTO.getToken();
        int tokenFlag = Integer.parseInt(RedisUtils.get("token:login:token:" + token));
        // 用户校验成功给用户登录
        if (tokenFlag > 0) {
            SysUser user = sysUserService.getUserInfoWithId(Long.parseLong(StpUtil.getLoginIdByToken(token).toString()));
            AsserUtils.isNull(user, "用户不存在");
            loginSuccess(channel, user, tokenName, token);
        } else {
            // 通知到前端的token失效
            sendMsg(channel, WsAdapter.buildInvalidateTokenVO());
        }
    }

    /**
     * 推动消息给所有在线的人
     *
     * @param wsBaseVO 发送的消息体
     * @param skipUid  需要跳过的人
     */
    @Override
    public void sendToAllOnline(WsBaseVO<?> wsBaseVO, Long skipUid) {
        ONLINE_USERID_MAP.forEach((userId, copyOnWriteArrayList) -> {
            if (Objects.nonNull(skipUid) && Objects.equals(userId, skipUid)) {
                return;
            }
            threadPoolTaskExecutor.execute(() -> sendMsg(copyOnWriteArrayList.get(0), wsBaseVO));
        });
    }

    /**
     * 推动消息给所有在线的人
     *
     * @param wsBaseVO 发送的消息体
     */
    @Override
    public void sendToAllOnline(WsBaseVO<?> wsBaseVO) {
        sendToAllOnline(wsBaseVO, null);
    }

    /**
     * 发送到用户 ID
     *
     * @param wsBaseResp WS 基础 RESP
     * @param userId     用户 ID
     * @author qingmeng
     * @createTime: 2023/11/23 16:22:54
     */
    @Override
    public void sendToUserId(WsBaseVO<?> wsBaseResp, Long userId) {
        CopyOnWriteArrayList<Channel> channels = ONLINE_USERID_MAP.get(userId);
        if (CollectionUtil.isEmpty(channels)) {
            return;
        }
        channels.forEach(channel -> {
            threadPoolTaskExecutor.execute(() -> sendMsg(channel, wsBaseResp));
        });
    }

    /**
     * 扫描登录成功
     *
     * @param loginCode 登录代码
     * @param userId    用户 ID
     * @author qingmeng
     * @createTime: 2023/11/20 09:00:45
     */
    @Override
    public void scanLoginSuccess(Integer loginCode, Long userId) {
        // 确认连接在该机器
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(loginCode);
        if (Objects.isNull(channel)) {
            return;
        }
        SysUser sysUser = sysUserService.getUserInfoWithId(userId);
        // 移除code
        WAIT_LOGIN_MAP.invalidate(loginCode);
        // 调用用户登录模块
        StpUtil.login(userId, new SaLoginModel()
                .setDevice(LoginDeviceEnum.PC.getDevice())
                .setIsLastingCookie(true)
                .setExtra("userName", sysUser.getUserName())
        );
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        // 推送登陆成功消息
        loginSuccess(channel, sysUser, saTokenInfo.getTokenName(), saTokenInfo.getTokenValue());
    }


    /**
     * (channel必在本地)登录成功，并更新状态
     */
    private void loginSuccess(Channel channel, SysUser sysUser, String tokenName, String tokenValue) {
        //更新上线列表
        online(channel, sysUser.getId());
        // 发送给对应的用户
        sendMsg(channel, WsAdapter.buildLoginSuccessVO(sysUser, tokenName, tokenValue));
        // 发送用户上线事件
        boolean online = userCache.isOnline(sysUser.getId());
        if (!online) {
            sysUser.setLastOperateTime(new Date());
            sysUser.setIpLocation(IpUtils.getIpHomeLocal(NettyUtil.getAttr(channel, NettyUtil.IP)));
            applicationEventPublisher.publishEvent(new UserOnlineEvent(this, sysUser));
        }
    }

    /**
     * 用户上线
     */
    private void online(Channel channel, Long userId) {
        getOrInitChannelExt(channel).setUserId(userId);
        ONLINE_USERID_MAP.putIfAbsent(userId, new CopyOnWriteArrayList<>());
        ONLINE_USERID_MAP.get(userId).add(channel);
        NettyUtil.setAttr(channel, NettyUtil.USERID, userId);
    }

    /**
     * 如果在线列表不存在，就先把该channel放进连接列表
     *
     * @param channel 连接的通道
     * @return WsChannelExtraDTO
     */
    private WsChannelExtraDTO getOrInitChannelExt(Channel channel) {
        WsChannelExtraDTO wsChannelExtraDTO = CONNECT_WS_MAP.getOrDefault(channel, new WsChannelExtraDTO());
        WsChannelExtraDTO old = CONNECT_WS_MAP.putIfAbsent(channel, wsChannelExtraDTO);
        return ObjectUtil.isNull(old) ? wsChannelExtraDTO : old;
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
     * @param channel  连接的通道
     * @param wsBaseVO 要发送的 WebSocket 消息
     */
    private void sendMsg(Channel channel, WsBaseVO<?> wsBaseVO) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsBaseVO)));
    }
}
