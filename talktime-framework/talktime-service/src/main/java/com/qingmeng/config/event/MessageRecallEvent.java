package com.qingmeng.config.event;

import com.qingmeng.config.netty.vo.WsMsgRecallVO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 10:56:00
 */
@Getter
public class MessageRecallEvent extends ApplicationEvent {
    private static final long serialVersionUID = 4322329271192326953L;
    private final WsMsgRecallVO recallDTO;

    public MessageRecallEvent(Object source, WsMsgRecallVO recallDTO) {
        super(source);
        this.recallDTO = recallDTO;
    }
}
