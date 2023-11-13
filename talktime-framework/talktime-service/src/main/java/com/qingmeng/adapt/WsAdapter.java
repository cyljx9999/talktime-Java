package com.qingmeng.adapt;

import com.qingmeng.netty.enums.WSResponseTypeEnum;
import com.qingmeng.netty.vo.WsBaseVO;
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
}
