package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 好友申请表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
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
     * 申请加好友的目标id
     */
    private Long targetId;

    /**
     * 申请信息
     */
    private String applyMsg;

    /**
     * 申请状态 0 申请中 1已通过 2已拒绝
     */
    private Integer applyStatus;

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
     * 逻辑删除 0未删除 1删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;
}