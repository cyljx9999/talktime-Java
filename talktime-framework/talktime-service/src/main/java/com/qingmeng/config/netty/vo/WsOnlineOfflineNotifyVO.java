package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户上下线变动的推送类
 * @createTime 2023年11月13日 10:47:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsOnlineOfflineNotifyVO {
    /**
     * 新的上下线用户
     */
    private List<ChatMemberVO> changeList;

    /**
     * 在线人数
     */
    private Long onlineNum;
}
