package com.qingmeng.config.rabbitmq.config;

import com.qingmeng.constant.RabbitMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
public class CommonChatQueueConfig {
    /**
     * 聊天队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue commonChatQueue() {
        return new Queue(RabbitMqConstant.COMMON_FANOUT_CHAT_QUEUE_NAME);
    }

    @Bean
    public FanoutExchange commonFanoutExchange() {
        return new FanoutExchange(RabbitMqConstant.COMMON_FANOUT_CHAT_EXCHANGE_NAME);
    }

    /**
     * 绑定队列一到交换机
     *
     * @return 绑定对象
     */
    @Bean
    public Binding bingCommonChatQueueToExchange() {
        return BindingBuilder.bind(commonChatQueue()).to(commonFanoutExchange());
    }

}
