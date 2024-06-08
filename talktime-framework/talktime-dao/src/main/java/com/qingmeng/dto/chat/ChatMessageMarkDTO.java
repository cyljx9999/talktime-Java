package com.qingmeng.dto.chat;

import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息标记请求
 * @createTime 2024年06月08日 13:25:00
 */
@Data
public class ChatMessageMarkDTO {
    /**
     * 消息id
     */
    @NotNull
    private Long msgId;

    /**
     * 标记类型 1点赞 2举报
     * @see com.qingmeng.enums.chat.MessageMarkTypeEnum
     */
    @NotNull
    @IntListValue(values = {1, 2})
    private Integer markType;

    /**
     * 动作类型 1确认 2取消
     * @see com.qingmeng.enums.chat.MessageMarkActTypeEnum
     */
    @NotNull
    @IntListValue(values = {1, 2})
    private Integer actType;
}
