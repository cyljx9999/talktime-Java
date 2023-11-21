package com.qingmeng.adapt;

import com.qingmeng.entity.SysUser;
import com.qingmeng.netty.enums.WSResponseTypeEnum;
import com.qingmeng.netty.vo.WsBaseVO;
import com.qingmeng.netty.vo.WsLoginSuccessVO;
import com.qingmeng.netty.vo.WsLoginUrlVO;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

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
}
