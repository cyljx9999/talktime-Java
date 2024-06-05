package com.qingmeng.dto.chat;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息参数
 * @createTime 2024年06月04日 21:12:00
 */
@Data
public class ChatMessageDTO {
    @NotNull
    private Long roomId;

    @NotNull
    private Integer msgType;

    /**
     * 消息类型体
     * @see com.qingmeng.dto.chat.msg
     */
     @NotNull
    private Object body;
}
