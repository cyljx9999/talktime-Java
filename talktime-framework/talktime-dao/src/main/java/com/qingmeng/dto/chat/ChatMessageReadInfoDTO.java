package com.qingmeng.dto.chat;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 聊天消息 阅读信息 DTO
 *
 * @author qingmeng
 * @date 2024/06/09 19:00:04
 */
@Data
public class ChatMessageReadInfoDTO {
    /**
     * 消息 ID
     */
    @Size(max = 20)
    private List<Long> msgIds;
}
