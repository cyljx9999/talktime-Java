package com.qingmeng.adapt;

import cn.hutool.core.bean.BeanUtil;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.user.UsageStatusEnum;
import com.qingmeng.netty.enums.WSResponseTypeEnum;
import com.qingmeng.netty.vo.*;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.util.Collections;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ws适配器
 * @createTime 2023年11月13日 14:33:00
 */
public class WsAdapter {
    /**
     * 构造获取登录二维码返回类
     *
     * @param wxMpQrCodeTicket 微信二维码对象
     * @return {@link WsBaseVO }<{@link WsLoginUrlVO }>
     * @author qingmeng
     * @createTime: 2023/11/13 14:34:31
     */
    public static WsBaseVO<WsLoginUrlVO> buildLoginQrcode(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WsBaseVO<WsLoginUrlVO> wsBaseResp = new WsBaseVO<>();
        wsBaseResp.setType(WSResponseTypeEnum.LOGIN_URL.getType());
        wsBaseResp.setData(WsLoginUrlVO.builder().loginUrl(wxMpQrCodeTicket.getUrl()).build());
        return wsBaseResp;
    }


    /**
     * 构建扫描成功
     *
     * @return {@link WsBaseVO }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/20 09:58:45
     */
    public static WsBaseVO<String> buildScanSuccessVO() {
        WsBaseVO<String> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WSResponseTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return wsBaseVO;
    }

    /**
     * 构建登录成功 vo
     *
     * @param sysUser    sys 用户
     * @param tokenName  令牌名称
     * @param tokenValue 令牌值
     * @return {@link WsBaseVO }<{@link WsLoginSuccessVO }>
     * @author qingmeng
     * @createTime: 2023/11/21 22:56:20
     */
    public static WsBaseVO<WsLoginSuccessVO> buildLoginSuccessVO(SysUser sysUser, String tokenName,String tokenValue) {
        WsBaseVO<WsLoginSuccessVO> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WSResponseTypeEnum.LOGIN_SUCCESS.getType());
        WsLoginSuccessVO wsLoginSuccess = WsLoginSuccessVO.builder()
                .userId(sysUser.getId())
                .avatar(sysUser.getUserName())
                .userName(sysUser.getUserName())
                .tokenName(tokenName)
                .tokenValue(tokenValue)
                .build();
        wsBaseVO.setData(wsLoginSuccess);
        return wsBaseVO;
    }

    /**
     * 构建无效令牌 VO
     *
     * @return {@link WsBaseVO }<{@link WsLoginSuccessVO }>
     * @author qingmeng
     * @createTime: 2023/11/21 20:06:08
     */
    public static WsBaseVO<WsLoginSuccessVO> buildInvalidateTokenVO() {
        WsBaseVO<WsLoginSuccessVO> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WSResponseTypeEnum.INVALIDATE_TOKEN.getType());
        return wsBaseVO;
    }

    /**
     * 构建离线通知返回类
     *
     * @param sysUser sys 用户
     * @return {@link WsBaseVO }<{@link WsOnlineOfflineNotifyVO }>
     * @author qingmeng
     * @createTime: 2023/11/22 23:51:53
     */
    public static WsBaseVO<WsOnlineOfflineNotifyVO> buildOfflineNotifyVO(SysUser sysUser) {
        WsBaseVO<WsOnlineOfflineNotifyVO> wsBaseResp = new WsBaseVO<>();
        wsBaseResp.setType(WSResponseTypeEnum.ONLINE_OFFLINE_NOTIFY.getType());
        WsOnlineOfflineNotifyVO onlineOfflineNotify = new WsOnlineOfflineNotifyVO();
        onlineOfflineNotify.setChangeList(Collections.singletonList(buildOfflineInfo(sysUser)));
        //todo 统计在线人数;
        wsBaseResp.setData(onlineOfflineNotify);
        return wsBaseResp;
    }

    /**
     * 生成离线信息返回类
     *
     * @param sysUser sys 用户
     * @return {@link ChatMemberVO }
     * @author qingmeng
     * @createTime: 2023/11/22 23:54:10
     */
    private static ChatMemberVO buildOfflineInfo(SysUser sysUser) {
        ChatMemberVO chatMemberVO = new ChatMemberVO();
        BeanUtil.copyProperties(sysUser, chatMemberVO);
        chatMemberVO.setUserId(sysUser.getId());
        chatMemberVO.setActiveStatus(UsageStatusEnum.ON_LINE.getCode());
        chatMemberVO.setLastOptTime(sysUser.getLastOperateTime());
        return chatMemberVO;
    }
}
