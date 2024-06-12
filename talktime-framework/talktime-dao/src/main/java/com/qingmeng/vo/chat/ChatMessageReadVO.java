package com.qingmeng.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息 阅读 VO
 *
 * @author qingmeng
 * @date 2024/06/09 19:33:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageReadVO {

    /**
     * 已读或者未读的用户uid
     */
    private Long userId;
}
