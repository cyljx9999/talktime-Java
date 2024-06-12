package com.qingmeng.dto.chat;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 聊天消息读取 DTO
 *
 * @author qingmeng
 * @date 2024/06/09 19:24:07
 */
@Data
public class ChatMessageReadDTO {
    /**
     * 房间 ID
     */
    @NotNull
    private Long roomId;
}
