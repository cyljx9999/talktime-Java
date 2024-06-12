package com.qingmeng.vo.chat.child;

import lombok.Data;

import java.util.Date;

/**
 * 客房基本信息
 *
 * @author qingmeng
 * @date 2024/06/12 14:58:54
 */
@Data
public class SessionInfo {
    /**
     * 房间 ID
     */
    private Long roomId;

    /**
     * 会话名称
     */
    private String sessionName;

    /**
     * 会话头像
     */
    private String sessionAvatar;

    /**
     * 房间类型 1群聊 2单聊
     */
    private Integer roomType;

    /**
     * 群最后消息的更新时间
     */
    private Date activeTime;

    /**
     * 最后一条消息id
     */
    private Long lastMsgId;
}
