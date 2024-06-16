package com.qingmeng.config.adapt;

import cn.hutool.core.bean.BeanUtil;
import com.qingmeng.config.netty.enums.WSResponseTypeEnum;
import com.qingmeng.config.netty.vo.*;
import com.qingmeng.dto.chat.ChatMessageOtherMarkDTO;
import com.qingmeng.entity.SysArticle;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.user.OnlineStatusEnum;
import com.qingmeng.vo.chat.ChatMessageVO;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.BeanUtils;

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
                .avatar(sysUser.getUserAvatar())
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
        chatMemberVO.setActiveStatus(OnlineStatusEnum.OFF_LINE.getCode());
        chatMemberVO.setLastOptTime(sysUser.getLastOperateTime());
        return chatMemberVO;
    }

    /**
     * 建立在线通知VO
     *
     * @param sysUser sys 用户
     * @return {@link WsBaseVO }<{@link WsOnlineOfflineNotifyVO }>
     * @author qingmeng
     * @createTime: 2023/11/23 16:19:58
     */
    public static WsBaseVO<WsOnlineOfflineNotifyVO> buildOnlineNotifyVO(SysUser sysUser) {
        WsBaseVO<WsOnlineOfflineNotifyVO> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WSResponseTypeEnum.ONLINE_OFFLINE_NOTIFY.getType());
        WsOnlineOfflineNotifyVO wsOnlineOfflineNotifyVO = new WsOnlineOfflineNotifyVO();
        wsOnlineOfflineNotifyVO.setChangeList(Collections.singletonList(buildOnlineInfo(sysUser)));
        wsBaseVO.setData(wsOnlineOfflineNotifyVO);
        return wsBaseVO;
    }

    /**
     * 建立在线信息
     *
     * @param sysUser sys 用户
     * @return {@link ChatMemberVO }
     * @author qingmeng
     * @createTime: 2023/11/23 16:21:25
     */
    private static ChatMemberVO buildOnlineInfo(SysUser sysUser) {
        ChatMemberVO chatMemberVO = new ChatMemberVO();
        BeanUtil.copyProperties(sysUser, chatMemberVO);
        chatMemberVO.setUserId(sysUser.getId());
        chatMemberVO.setActiveStatus(OnlineStatusEnum.ON_LINE.getCode());
        chatMemberVO.setLastOptTime(sysUser.getLastOperateTime());
        return chatMemberVO;
    }

    /**
     * 构建申请好友信息
     *
     * @return {@link WsBaseVO }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/28 16:53:18
     */
    public static WsBaseVO<String> buildApplyInfoVO() {
        WsBaseVO<String> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WSResponseTypeEnum.APPLY.getType());
        return wsBaseVO;
    }

    /**
     * 构建 msg 发送
     *
     * @param chatMessageVO 聊天消息 VO
     * @return {@link WsBaseVO }<{@link ChatMessageVO }>
     * @author qingmeng
     * @createTime: 2024/06/08 18:51:28
     */
    public static WsBaseVO<ChatMessageVO> buildMsgSend(ChatMessageVO chatMessageVO) {
        WsBaseVO<ChatMessageVO> wsBaseResp = new WsBaseVO<>();
        wsBaseResp.setType(WSResponseTypeEnum.MESSAGE.getType());
        wsBaseResp.setData(chatMessageVO);
        return wsBaseResp;
    }

    /**
     * 构建 消息标记 发送
     *
     * @param dto       DTO
     * @param markCount 标记计数
     * @return {@link WsBaseVO }<{@link WsMsgMarkVO }>
     * @author qingmeng
     * @createTime: 2024/06/08 18:54:37
     */
    public static WsBaseVO<WsMsgMarkVO> buildMsgMarkSend(ChatMessageOtherMarkDTO dto, Long markCount) {
        WsMsgMarkVO.WsMsgMarkItem item = new WsMsgMarkVO.WsMsgMarkItem();
        BeanUtils.copyProperties(dto, item);
        item.setMarkCount(markCount);
        WsBaseVO<WsMsgMarkVO> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WSResponseTypeEnum.MARK.getType());
        WsMsgMarkVO mark = new WsMsgMarkVO();
        mark.setMarkList(Collections.singletonList(item));
        wsBaseVO.setData(mark);
        return wsBaseVO;
    }

    /**
     * 构建发送物品通知
     *
     * @param article 品
     * @return {@link WsBaseVO }<{@link WsSendItemInform }>
     * @author qingmeng
     * @createTime: 2024/06/12 17:21:13
     */
    public static WsBaseVO<WsSendItemInform> buildSendUserItemInform(SysArticle article) {
        WsBaseVO<WsSendItemInform> wsBaseResp = new WsBaseVO<>();
        wsBaseResp.setType(WSResponseTypeEnum.SYSTEM.getType());
        WsSendItemInform wsSendItemInform = new WsSendItemInform();
        wsSendItemInform.setItemName(article.getArticleDescribe());
        wsBaseResp.setData(wsSendItemInform);
        return wsBaseResp;
    }
}
