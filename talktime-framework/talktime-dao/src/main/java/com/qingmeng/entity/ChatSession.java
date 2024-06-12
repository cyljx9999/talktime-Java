package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天会话
 *
 * @author qingmeng
 * @date 2024/06/09 19:15:46
 */
@Data
@TableName("chat_session")
public class ChatSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId( type = IdType.AUTO)
    private Long id;

    /**
     * uid
     */
    private Long userId;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 阅读到的时间
     */
    private Date readTime;

    /**
     * 会话内消息最后更新的时间
     */
    private Date activeTime;

    /**
     * 最后一条消息id
     */
    private Long lastMsgId;

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
