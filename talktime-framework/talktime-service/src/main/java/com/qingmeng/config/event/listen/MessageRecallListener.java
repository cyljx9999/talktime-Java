package com.qingmeng.config.event.listen;

import com.qingmeng.config.cache.MsgCache;
import com.qingmeng.config.event.MessageRecallEvent;
import com.qingmeng.config.netty.vo.WsMsgRecallVO;
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

    @Async("visibleTaskExecutor")
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void evictMsg(MessageRecallEvent event) {
        WsMsgRecallVO recallDTO = event.getRecallDTO();
        msgCache.evictMsg(recallDTO.getMsgId());
    }

    @Async("visibleTaskExecutor")
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void sendToAll(MessageRecallEvent event) {
        // todo mq推送消息
        //pushService.sendPushMsg(WSAdapter.buildMsgRecall(event.getRecallDTO()));
    }

}
