package com.qingmeng.rabbitmq.producer;

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
public class RabbitmqProducer<T> {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送可靠消息
     *
     * @param data 数据
     * @author qingmeng
     * @createTime: 2023/11/21 15:24:21
     */
    public void sendReliableMsgBySimpleMode(T data,String queueName) {
        rabbitTemplate.convertAndSend(
                queueName,
                data
        );
    }

    /**
     * 发送消息
     *
     * @param data 数据
     * @author qingmeng
     * @createTime: 2023/11/21 15:24:26
     */
    public void sendMsgBySimpleMode(T data,String queueName) {
        rabbitTemplate.convertAndSend(
                queueName,
                data
        );
    }
}
