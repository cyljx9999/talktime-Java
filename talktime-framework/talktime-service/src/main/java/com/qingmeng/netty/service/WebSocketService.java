package com.qingmeng.netty.service;

import com.qingmeng.dto.login.WsAuthorizeDTO;
import com.qingmeng.netty.vo.WsBaseVO;
import io.netty.channel.Channel;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 处理websocket所有逻辑
 * @createTime 2023年11月13日 11:33:00
 */
public interface WebSocketService {

    /**
     * ws连接的事件
     * @param channel 连接的通道
     */
    void connect(Channel channel);

    /**
     * 处理用户登录请求，需要返回一张带code的二维码
     *
     * @param channel 连接的通道
     */
    void getLoginQrcode(Channel channel);

    /**
     * 移除ws连接的事件
     * @param channel 连接的通道
     */
    void removeConnect(Channel channel);

    /**
     * 扫描登录成功
     *
     * @param loginCode 登录代码
     * @param userId    用户 ID
     * @author qingmeng
     * @createTime: 2023/11/20 09:00:45
     */
    void scanLoginSuccess(Integer loginCode, Long userId);

    /**
     * 扫描成功
     *
     * @param loginCode 登录代码
     * @author qingmeng
     * @createTime: 2023/11/20 09:57:07
     */
    void scanSuccess(Integer loginCode);

    /**
     * 授权
     *
     * @param channel      渠道
     * @param authorizeDTO 授权 DTO
     * @author qingmeng
     * @createTime: 2023/11/21 19:35:25
     */
    void authorize(Channel channel, WsAuthorizeDTO authorizeDTO);

    /**
     * 推动消息给所有在线的人
     *
     * @param wsBaseVO 发送的消息体
     * @param skipUid    需要跳过的人
     */
    void sendToAllOnline(WsBaseVO<?> wsBaseVO, Long skipUid);

    /**
     * 推动消息给所有在线的人
     *
     * @param wsBaseVO 发送的消息体
     */
    void sendToAllOnline(WsBaseVO<?> wsBaseVO);

}

