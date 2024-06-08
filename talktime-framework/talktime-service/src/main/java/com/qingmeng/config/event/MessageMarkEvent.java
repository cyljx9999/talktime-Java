package com.qingmeng.config.event;

import com.qingmeng.dto.chat.ChatMessageOtherMarkDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月08日 13:52:00
 */
@Getter
public class MessageMarkEvent extends ApplicationEvent {
    private static final long serialVersionUID = 2137101635033366698L;
    private final ChatMessageOtherMarkDTO dto;

    public MessageMarkEvent(Object source, ChatMessageOtherMarkDTO dto) {
        super(source);
        this.dto = dto;
    }
}
