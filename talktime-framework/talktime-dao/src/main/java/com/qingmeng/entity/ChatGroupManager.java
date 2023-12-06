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
 * 群管理员表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Getter
@Setter
@TableName("chat_group_manager")
public class ChatGroupManager implements Serializable {


    private static final long serialVersionUID = 1078593710209197835L;
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色类型
     * @see com.qingmeng.enums.chat.GroupRoleEnum
     */
    private Integer roleType;

    /**
     * 群聊房间id
     */
    private Long roomGroupId;

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
