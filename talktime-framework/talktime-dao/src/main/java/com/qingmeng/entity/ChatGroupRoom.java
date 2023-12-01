package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.system.LogicDeleteEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群聊表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_group_room")
public class ChatGroupRoom implements Serializable {


    private static final long serialVersionUID = 5479581107333739303L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 群聊状态
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