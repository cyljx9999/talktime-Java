package com.qingmeng.vo.chat.child;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 11:28:00
 */
@Data
public class ReplyMsg {
    /**
     * 消息id
     */
    private Long id;

    /**
     * 用户userId
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 消息类型
     * @see com.qingmeng.enums.chat.MessageTypeEnum
     */
    private Integer messageType;

    /**
     * 消息内容不同的消息类型，见父消息内容体
     */
    private Object body;

    /**
     * 是否可消息跳转 0否 1是
     */
    private Integer canCallback;

    /**
     * 跳转间隔的消息条数
     */
    private Long gapCount;
}
