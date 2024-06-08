package com.qingmeng.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息标记请求
 * @createTime 2024年06月08日 13:25:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageOtherMarkDTO {
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 标记类型 1点赞 2举报
     * @see com.qingmeng.enums.chat.MessageMarkTypeEnum
     */
    private Integer markType;

    /**
     * 动作类型 1确认 2取消
     * @see com.qingmeng.enums.chat.MessageMarkActTypeEnum
     */
    private Integer actType;
}
