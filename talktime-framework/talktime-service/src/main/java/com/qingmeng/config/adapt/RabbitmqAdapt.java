package com.qingmeng.config.adapt;

import com.qingmeng.constant.RabbitMqConstant;
import com.qingmeng.entity.SysRabbitmqLog;
import com.qingmeng.enums.system.RabbitmqSendEnum;
import com.qingmeng.utils.IdUtils;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 23:44:00
 */
public class RabbitmqAdapt {

    public static SysRabbitmqLog getMsgLog(Object data){
        SysRabbitmqLog rabbitmqLog = new SysRabbitmqLog();
        String signId = IdUtils.simpleUUID();
        rabbitmqLog.setMsgId(signId);
        rabbitmqLog.setData(String.valueOf(data));
        rabbitmqLog.setStatus(RabbitmqSendEnum.SENDING.getCode());
        rabbitmqLog.setQueue(RabbitMqConstant.RELIABLE_FANOUT_CHAT_QUEUE_NAME);
        rabbitmqLog.setExchange(RabbitMqConstant.RELIABLE_FANOUT_CHAT_EXCHANGE_NAME);
        rabbitmqLog.setCount(0);
        // 设置tryTime为一分钟后重试
        rabbitmqLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * 1));
        rabbitmqLog.setCreateTime(new Date());
        rabbitmqLog.setUpdateTime(new Date());
        return rabbitmqLog;
    }

}
