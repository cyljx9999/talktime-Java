package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群聊个人设置
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_group_personal_setting")
public class ChatGroupPersonalSetting implements Serializable {


    private static final long serialVersionUID = 5590302068175253871L;
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
     * 置顶状态 0不置顶 1置顶
     * @see com.qingmeng.enums.chat.MessageTopStatusEnum
     */
    private Integer topStatus;

    /**
     * 展示群成员名字状态 0隐藏 1开启
     * @see com.qingmeng.enums.chat.DisplayNameStatusEnum
     */
    private Integer displayNameStatus;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 提醒状态 0关闭 1开启
     * @see com.qingmeng.enums.common.OpenStatusEnum
     */
    private Integer remindStatus;

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
