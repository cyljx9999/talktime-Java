package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.system.LogicDeleteEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群聊设置
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_group_setting")
public class ChatGroupSetting implements Serializable {


    private static final long serialVersionUID = -8866470944491730569L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群聊房间
     */
    private Long groupRoomId;

    /**
     * 群聊名字
     */
    private String groupRoomName;

    /**
     * 群聊头像
     */
    private String groupRoomAvatar;

    /**
     * 群聊二维码
     */
    private String groupRoomQrcode;

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
