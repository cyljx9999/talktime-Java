package com.qingmeng.dto.chat;

import com.qingmeng.valid.custom.IntListValue;
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
    /**
     * 房间 ID
     */
    @NotNull
    private Long roomId;

    /**
     * 消息类型
     * @see com.qingmeng.enums.chat.MessageTypeEnum
     */
    @NotNull
    @IntListValue(values = {1, 2, 3, 4, 5, 6, 7, 8})
    private Integer msgType;

    /**
     * 消息类型体
     *
     * @see com.qingmeng.dto.chat.msg
     */
    @NotNull
    private Object body;
}
