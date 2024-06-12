package com.qingmeng.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomVO {
    /**
     * 房间 ID
     */
    private Long roomId;

    /**
     * 房间类型 1群聊 2单聊
     */
    private Integer roomType;

    /**
     * 最新消息
     */
    private String text;

    /**
     * 会话名称
     */
    private String sessionName;

    /**
     * 会话头像
     */
    private String sessionAvatar;

    /**
     * 房间最后活跃时间(用来排序)
     */
    private Date activeTime;

    /**
     * 未读数
     */
    private Long unreadCount;
}
