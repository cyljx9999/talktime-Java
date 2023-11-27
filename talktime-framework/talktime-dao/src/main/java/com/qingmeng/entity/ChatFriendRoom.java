package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.chat.RoomStatusEnum;
import com.qingmeng.enums.system.LogicDeleteEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 单聊表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_friend_room")
public class ChatFriendRoom implements Serializable {


    private static final long serialVersionUID = -6524058172859594500L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 好友id
     */
    private Long userFriendId;

    /**
     * 根据双方id组成的唯一标识
     */
    private String roomKey;

    /**
     * 房间状态 0正常 1删除
     * @see RoomStatusEnum
     */
    private Integer roomStatus;

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

    /**
     * 逻辑删除
     * @see LogicDeleteEnum
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
