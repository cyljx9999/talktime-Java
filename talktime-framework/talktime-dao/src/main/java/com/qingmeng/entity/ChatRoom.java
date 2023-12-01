package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 聊天房间
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_room")
public class ChatRoom implements Serializable {


    private static final long serialVersionUID = -1734679074180357303L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间状态 0好友 1群聊
     * @see com.qingmeng.enums.chat.RoomTypeEnum
     */
    private Integer roomType;

    /**
     * 最新一条信息id
     */
    private Long lastMsgId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
