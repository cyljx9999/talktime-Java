package com.qingmeng.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description rabbitmq发送状态
 * @createTime 2023年11月21日 15:07:00
 */
@Getter
@AllArgsConstructor
public enum RabbitmqSendEnum {

    /**
     *  0 消息投递中   1 投递成功   2投递失败
     */
    SENDING(0, "消息投递中"),
    SEND_SUCCESS(1, "投递成功"),
    SEND_FAIL(2, "投递失败")
    ;

    private final int code;
    private final String msg;

}
