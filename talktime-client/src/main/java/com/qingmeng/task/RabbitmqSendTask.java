package com.qingmeng.task;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysRabbitmqLogDao;
import com.qingmeng.entity.SysRabbitmqLog;
import com.qingmeng.enums.system.RabbitmqQueueTypeEnum;
import com.qingmeng.enums.system.RabbitmqSendEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 查询信息是否成功发送到mq
 * @createTime 2023年04月16日 15:07:00
 */
@Component
@Slf4j
public class RabbitmqSendTask {
    @Resource
    SysRabbitmqLogDao sysRabbitmqLogDao;
    @Resource
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/60 * * * * ?")
    public void rabbitmqSendTask() {
        LambdaQueryWrapper<SysRabbitmqLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRabbitmqLog::getStatus, 0).lt(SysRabbitmqLog::getTryTime, DateUtil.date());
        List<SysRabbitmqLog> logs = sysRabbitmqLogDao.list(queryWrapper);
        if (logs == null || logs.isEmpty()) {
            return;
        }
        logs.forEach(rabbitmqLog -> {
            log.info("开始执行rabbitmq重新消息");
            if (rabbitmqLog.getCount() >= SystemConstant.MAX_RETRY_COUNT) {
                //直接设置该条消息发送失败
                SysRabbitmqLog sysRabbitmqLog = new SysRabbitmqLog();
                sysRabbitmqLog.setMsgId(rabbitmqLog.getMsgId());
                sysRabbitmqLog.setStatus(RabbitmqSendEnum.SEND_FAIL.getCode());
                sysRabbitmqLogDao.updateById(sysRabbitmqLog);
                log.info("rabbitmq重新消息达到最大次数");
            } else {
                // 更新rabbitmq记录信息
                SysRabbitmqLog sysRabbitmqLog = new SysRabbitmqLog();
                sysRabbitmqLog.setMsgId(rabbitmqLog.getMsgId());
                sysRabbitmqLog.setCount(rabbitmqLog.getCount() + 1);
                sysRabbitmqLog.setTryTime(new Date());
                sysRabbitmqLogDao.updateById(sysRabbitmqLog);
                // 延迟队列信息重发
                if (RabbitmqQueueTypeEnum.DELAY_QUEUE.getCode().equals(rabbitmqLog.getFlag())) {

                } else if (RabbitmqQueueTypeEnum.NORMAL_QUEUE.getCode().equals(rabbitmqLog.getFlag())) {
                    // 默认交换机重新发送信息
                    rabbitTemplate.convertAndSend(
                            rabbitmqLog.getQueue(),
                            (Object) rabbitmqLog.getData(),
                            new CorrelationData(rabbitmqLog.getMsgId())
                    );
                }else if (RabbitmqQueueTypeEnum.EXCHANGE_QUEUE.getCode().equals(rabbitmqLog.getFlag())) {
                    // 交换机重新发送信息
                    rabbitTemplate.convertAndSend(
                            rabbitmqLog.getExchange(),
                            (Object) rabbitmqLog.getData(),
                            new CorrelationData(rabbitmqLog.getMsgId())
                    );
                }

            }
        });
    }
}
