package com.qingmeng.config.rabbitmq.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月21日 15:22:00
 */
@Service
public class RabbitmqProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送可靠消息
     *
     * @param signId    签名 ID
     * @param data      数据
     * @param queueName 队列名称
     * @author qingmeng
     * @createTime: 2024/06/06 23:42:16
     */
    public void sendReliableMsg(String signId, Object data, String queueName) {
        rabbitTemplate.convertAndSend(
                queueName,
                data,
                new CorrelationData(signId)
        );
    }

    /**
     * 发送普通消息
     *
     * @param signId    签名 ID
     * @param data      数据
     * @param queueName 队列名称
     * @author qingmeng
     * @createTime: 2024/06/06 23:42:16
     */
    public void sendCommonMsg(String signId, Object data, String queueName) {
        rabbitTemplate.convertAndSend(
                queueName,
                data,
                new CorrelationData(signId)
        );
    }
}
