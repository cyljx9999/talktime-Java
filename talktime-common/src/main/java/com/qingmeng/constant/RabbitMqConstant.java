package com.qingmeng.constant;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description rabbitmq常量类
 * @createTime 2023年11月21日 15:17:02
 */
public class RabbitMqConstant {
    /**
     * 可靠聊天队列名字
     */
    public static final String RELIABLE_FANOUT_CHAT_QUEUE_NAME = "reliable.chat.queue";

    /**
     * 可靠交换机名称
     */
    public static final String RELIABLE_FANOUT_CHAT_EXCHANGE_NAME = "reliable.chat.exchange";

    /**
     * 普通聊天队列名字
     */
    public static final String COMMON_FANOUT_CHAT_QUEUE_NAME = "common.chat.queue";

    /**
     * 普通交换机名称
     */
    public static final String COMMON_FANOUT_CHAT_EXCHANGE_NAME = "common.chat.exchange";

}
