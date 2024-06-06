package com.qingmeng.config.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 23:01:00
 */
@Getter
public class MessageSendEvent extends ApplicationEvent {
    private static final long serialVersionUID = -1875702349973099452L;
    private final Long msgId;

    public MessageSendEvent(Object source, Long msgId) {
        super(source);
        this.msgId = msgId;
    }
}
