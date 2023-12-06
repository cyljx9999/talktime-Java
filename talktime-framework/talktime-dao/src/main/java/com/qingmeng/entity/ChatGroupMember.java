package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群聊成员表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_group_member")
public class ChatGroupMember implements Serializable {


    private static final long serialVersionUID = 3628007479054860867L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群聊房间id
     */
    private Long groupRoomId;

    /**
     * 用户id
     */
    private Long userId;

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
