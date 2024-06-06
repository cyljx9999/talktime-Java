package com.qingmeng.vo.chat;

import com.qingmeng.vo.chat.child.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 11:28:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVO {

    /**
     * 发送者id
     */
    private Long fromUserId;

    /**
     * 消息详情
     */
    private Message chatMessage;


}