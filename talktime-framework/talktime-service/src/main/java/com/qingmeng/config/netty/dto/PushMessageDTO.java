package com.qingmeng.config.netty.dto;

import com.qingmeng.config.netty.enums.WsPushTypeEnum;
import com.qingmeng.config.netty.vo.WsBaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 推送给用户的消息对象
 * @createTime 2024年06月08日 00:06:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushMessageDTO {
    /**
     * 推送的ws消息
     */
    private WsBaseVO<?> wsBaseVO;
    /**
     * 推送的userId
     */
    private List<Long> userIdList;

    /**
     * 推送类型 1 个人 2 全员
     *
     * @see WsPushTypeEnum
     */
    private Integer pushType;

    public PushMessageDTO(Long uid, WsBaseVO<?> vo) {
        this.userIdList = Collections.singletonList(uid);
        this.wsBaseVO = vo;
        this.pushType = WsPushTypeEnum.USER.getType();
    }

    public PushMessageDTO(List<Long> uidList, WsBaseVO<?> vo) {
        this.userIdList = uidList;
        this.wsBaseVO = vo;
        this.pushType = WsPushTypeEnum.USER.getType();
    }

    public PushMessageDTO(WsBaseVO<?> vo) {
        this.wsBaseVO = vo;
        this.pushType = WsPushTypeEnum.ONLINE_ALL_USER.getType();
    }
}
