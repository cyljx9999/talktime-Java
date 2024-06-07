package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.chat.ReadStatusEnum;
import com.qingmeng.enums.common.LogicDeleteEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 申请好友记录表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:21:12
 */
@Getter
@Setter
@TableName("sys_user_apply")
public class SysUserApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 申请状态 0 申请中 1已同意 2拒绝接受 3拉黑
     * @see com.qingmeng.enums.user.ApplyStatusEnum
     */
    private Integer applyStatus;

    /**
     * 目标好友id
     */
    private Long targetId;

    /**
     * 申请描述
     */
    private String applyDescribe;

    /**
     * 申请渠道
     */
    private String applyChannel;

    /**
     * 阅读状态 0未读 1已读
     * @see ReadStatusEnum
     */
    private Integer readStatus;

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
