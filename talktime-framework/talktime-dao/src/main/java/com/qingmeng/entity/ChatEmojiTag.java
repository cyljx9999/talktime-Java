package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("chat_emoji_tag")
public class ChatEmojiTag implements Serializable {

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
     * 标签名字	
     */
    private String tagName;

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
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
