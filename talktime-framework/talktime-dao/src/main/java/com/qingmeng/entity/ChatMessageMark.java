package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月07日 23:01:00
 */
@Data
@TableName("chat_message_mark")
public class ChatMessageMark implements Serializable {
    private static final long serialVersionUID = 1066160630497819643L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消息表id
     */
    private Long msgId;

    /**
     * 标记人uid
     */
    private Long userId;

    /**
     * 标记类型 1点赞 2举报
     *
     * @see com.qingmeng.enums.chat.MessageMarkTypeEnum
     */
    @TableField("type")
    private Integer type;

    /**
     * 消息状态 0正常 1取消
     */
    private Integer status;

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
