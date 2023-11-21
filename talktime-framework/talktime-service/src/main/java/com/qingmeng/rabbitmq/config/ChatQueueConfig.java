package com.qingmeng.rabbitmq.config;

import com.qingmeng.constant.RabbitMqConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description rabbitmq队列定义
 * @createTime 2023年11月12日 15:18:31
 */
@Configuration
public class ChatQueueConfig {
    /**
     * 聊天队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue chatQueue() {
        return new Queue(RabbitMqConstant.CHAT_QUEUE_NAME);
    }


}
