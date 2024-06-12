package com.qingmeng.dto.chat;

import com.qingmeng.domain.dto.CursorPageBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 聊天消息读取 DTO
 *
 * @author qingmeng
 * @date 2024/06/09 19:24:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessage1DTO extends CursorPageBaseDTO {
    /**
     * 房间 ID
     */
    @NotNull
    private Long roomId;
}
