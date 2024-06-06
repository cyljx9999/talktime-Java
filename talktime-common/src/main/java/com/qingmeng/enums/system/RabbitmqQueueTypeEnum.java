package com.qingmeng.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description rabbitmq队列类型
 * @createTime 2023年11月21日 15:07:00
 */
@Getter
@AllArgsConstructor
public enum RabbitmqQueueTypeEnum {

    /**
     * 0 为正常队列信息 1为延迟队列消息, 2为交换机消息
     */
    NORMAL_QUEUE(0),
    DELAY_QUEUE(1),
    EXCHANGE_QUEUE(2),
    ;

    private final Integer code;

}
