package com.qingmeng.config.rabbitmq.config;


import com.qingmeng.entity.SysRabbitmqLog;
import com.qingmeng.enums.system.RabbitmqSendEnum;
import com.qingmeng.service.SysRabbitmqLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description rabbitmq配置类
 * @createTime 2023年04月16日 15:30:00
 */
@Configuration
@Slf4j
public class RabbitmqConfig {
    @Resource
    CachingConnectionFactory cachingConnectionFactory;
    @Resource
    SysRabbitmqLogService sysRabbitmqLogService;

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            if(data != null) {
                String msgId = data.getId();
                if (ack) {
                    log.info(msgId + ":消息发送成功");
                    SysRabbitmqLog sysRabbitmqLog = new SysRabbitmqLog();
                    sysRabbitmqLog.setMsgId(msgId);
                    sysRabbitmqLog.setStatus(RabbitmqSendEnum.SEND_SUCCESS.getCode());
                    //修改数据库中的记录，消息投递成功
                    sysRabbitmqLogService.updateWithId(sysRabbitmqLog);
                } else {
                    log.error(msgId + ":消息发送失败");
                }
            }
        });
        rabbitTemplate.setReturnsCallback(returned -> log.error("消息发送失败"));
        return rabbitTemplate;
    }


}
