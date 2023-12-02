package com.qingmeng.vo.chat;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 简单的群聊信息返回类
 * @createTime 2023年12月02日 09:47:00
 */
@Data
public class SimpleChatInfoVO {

    /**
     * 群聊房间名称
     */
    private String groupRoomName;

    /**
     * 群聊房间头像
     */
    private String groupRoomAvatar;

}
