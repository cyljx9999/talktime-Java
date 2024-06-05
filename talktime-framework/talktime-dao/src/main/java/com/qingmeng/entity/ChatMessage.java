package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.qingmeng.dto.chat.msg.MessageExtra;
import com.qingmeng.enums.chat.MessageTypeEnum;
import com.qingmeng.enums.chat.RemindStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author qingmeng
 * @since 2024-06-04
 */
@Data
@TableName(value = "chat_message", autoResultMap = true)
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话表id
     */
    private Long roomId;

    /**
     * 消息发送者id
     */
    private Long fromUserId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 回复的消息内容
     */
    private Long replyMsgId;

    /**
     * 消息状态 0正常 1删除
     *
     * @see RemindStatusEnum
     */
    @TableField("status")
    private Integer status;

    /**
     * 与回复消息的间隔条数
     */
    private Integer gapCount;

    /**
     * 消息类型
     *
     * @see MessageTypeEnum
     */
    @TableField("type")
    private Integer messageType;

    /**
     * 消息扩展字段
     */
    @TableField(value = "extra", typeHandler = JacksonTypeHandler.class)
    private MessageExtra extra;

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
