package com.qingmeng.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义敏感词替换内容
 * @createTime 2023年05月12日 19:36:00
 */
@Data
public class SysSensitiveReplace implements Serializable {
    private static final long serialVersionUID = -504841648273353486L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long replaceId;

    /**
     * 目标名称
     */
    private String targetName;

    /**
     * 更换名字
     */
    private String replaceName;

    /**
     * 敏感词启动状态  0不启用 1启用
     * @see com.qingmeng.enums.common.OpenStatusEnum
     */
    private Integer isOpen;

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
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Integer isDeleted;


}
