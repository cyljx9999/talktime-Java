package com.qingmeng.config.event.listen;

import com.qingmeng.config.adapt.RabbitmqAdapt;
import com.qingmeng.config.event.MessageSendEvent;
import com.qingmeng.config.rabbitmq.producer.RabbitmqProducer;
import com.qingmeng.constant.RabbitMqConstant;
import com.qingmeng.dao.SysRabbitmqLogDao;
import com.qingmeng.entity.SysRabbitmqLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息发送监听器
 * @createTime 2024年06月06日 23:02:00
 */
@Component
public class MessageSendListener {
    @Resource
    private RabbitmqProducer rabbitmqProducer;
    @Resource
    private SysRabbitmqLogDao sysRabbitmqLogDao;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Long msgId = event.getMsgId();
        SysRabbitmqLog msgLog = RabbitmqAdapt.getMsgLog(msgId);
        sysRabbitmqLogDao.save(msgLog);
        rabbitmqProducer.sendReliableMsg(msgLog.getMsgId(),msgId, RabbitMqConstant.RELIABLE_FANOUT_CHAT_QUEUE_NAME);
    }
}
