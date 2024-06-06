package com.qingmeng.vo.chat.child;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 11:28:00
 */
@Data
public class Message {
    /**
     * 消息id
     */
    private Long id;

    /**
     * 房间 ID
     */
    private Long roomId;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;

    /**
     * 消息类型
     * @see com.qingmeng.enums.chat.MessageTypeEnum
     */
    private Integer messageType;

    /**
     * 消息内容不同的消息类型
     */
    private Object body;

    /**
     * 消息标记
     */
    private MessageMark messageMark;
}