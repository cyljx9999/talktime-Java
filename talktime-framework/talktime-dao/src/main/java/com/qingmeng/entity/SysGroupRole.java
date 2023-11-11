package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.chat.GroupRoleEnum;
import com.qingmeng.enums.system.LogicDeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 群组角色表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Data
@TableName("sys_group_role")
public class SysGroupRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群组角色名称
     * @see GroupRoleEnum
     */
    private String groupRoleName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

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
