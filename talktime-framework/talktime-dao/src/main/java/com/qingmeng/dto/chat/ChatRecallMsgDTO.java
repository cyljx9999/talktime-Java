package com.qingmeng.dto.chat;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 撤回消息参数
 * @createTime 2024年06月09日 18:36:00
 */
@Data
public class ChatRecallMsgDTO {
    /**
     * 消息 ID
     */
    @NotNull
    private Long msgId;

    /**
     * 房间 ID
     */
    @NotNull
    private Long roomId;
}
