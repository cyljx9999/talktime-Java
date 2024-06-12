package com.qingmeng.dto.chat;

import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 聊天消息读取类型 DTO
 *
 * @author qingmeng
 * @date 2024/06/09 19:30:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessageReadTypeDTO extends CursorPageBaseDTO {
    @NotNull
    private Long msgId;

    /**
     * 搜索类型
     * @see com.qingmeng.enums.chat.ReadStatusEnum
     */
    @NotNull
    @IntListValue(values = {0, 1})
    private Integer searchType;
}
