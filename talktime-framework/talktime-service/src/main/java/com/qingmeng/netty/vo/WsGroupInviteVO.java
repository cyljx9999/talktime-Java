package com.qingmeng.netty.vo;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群聊邀请
 * @createTime 2023年12月07日 09:07:00
 */
@Data
public class WsGroupInviteVO {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 群聊头像
     */
    private String groupAvatar;

}
