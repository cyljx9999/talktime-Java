package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表情包
 * </p>
 *
 * @author baomidou
 * @since 2024-06-15 01:01:47
 */
@Data
@TableName("chat_emoji")
public class ChatEmoji implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户表ID
     */
    private Long userId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 表情地址
     */
    private String expressionUrl;

    /**
     * 逻辑删除 0未删除 1删除
     * @see com.qingmeng.enums.common.LogicDeleteEnum
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
