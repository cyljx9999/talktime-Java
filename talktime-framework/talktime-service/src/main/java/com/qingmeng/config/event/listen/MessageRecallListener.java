package com.qingmeng.config.event.listen;

import com.qingmeng.config.cache.MsgCache;
import com.qingmeng.config.event.MessageRecallEvent;
import com.qingmeng.config.netty.dto.PushMessageDTO;
import com.qingmeng.config.netty.vo.WsBaseVO;
import com.qingmeng.config.netty.vo.WsMsgRecallVO;
import com.qingmeng.config.rabbitmq.producer.RabbitmqProducer;
import com.qingmeng.constant.RabbitMqConstant;
import com.qingmeng.enums.chat.MessageTypeEnum;
import com.qingmeng.utils.IdUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息撤回监听器
 * @createTime 2024年06月06日 11:00:00
 */
@Component
public class MessageRecallListener {
    @Resource
    private MsgCache msgCache;
    @Resource
    private RabbitmqProducer rabbitmqProducer;

    @Async("visibleTaskExecutor")
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void evictMsg(MessageRecallEvent event) {
        WsMsgRecallVO recallDTO = event.getRecallDTO();
        msgCache.evictMsg(recallDTO.getMsgId());
    }

    @Async("visibleTaskExecutor")
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void sendToAll(MessageRecallEvent event) {
        WsMsgRecallVO recallDTO = event.getRecallDTO();
        WsBaseVO<Object> vo = new WsBaseVO<>();
        vo.setData(recallDTO);
        vo.setType(MessageTypeEnum.RECALL.getType());
        PushMessageDTO pushMessageDTO = new PushMessageDTO(vo);
        rabbitmqProducer.sendCommonMsg(IdUtils.simpleUUID(),pushMessageDTO, RabbitMqConstant.COMMON_FANOUT_CHAT_QUEUE_NAME);
    }

}
