package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 敏感词
 * @createTime 2023年05月12日 18:16:00
 */
@Data
@TableName("sys_sensitive_word")
public class SysSensitiveWord implements Serializable {
    private static final long serialVersionUID = 4445865092932335033L;

    /**
     * 敏感词id
     */
    @TableId(type = IdType.AUTO)
    private Long sensitiveWordId;

    /**
     * 敏感词
     */
    private String sensitiveWord;

    /**
     * 敏感词类型 0为黑名单 1为白名单
     * @see com.qingmeng.enums.sensitive.SensitiveTypeEnum
     */
    private Integer sensitiveWordType;

    /**
     * 敏感词启动状态  0不启用 1启用
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
