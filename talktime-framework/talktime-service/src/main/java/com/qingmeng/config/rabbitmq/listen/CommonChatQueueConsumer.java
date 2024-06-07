package com.qingmeng.config.rabbitmq.listen;


import com.qingmeng.config.netty.dto.PushMessageDTO;
import com.qingmeng.config.netty.enums.WsPushTypeEnum;
import com.qingmeng.config.netty.service.WebSocketService;
import com.qingmeng.constant.RabbitMqConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * @author 清梦
 * @version 1.0.0
 * @Description 聊天队列的消费者
 * @createTime 2023年04月18日 00:27:35
 */
@Slf4j
@Component
public class CommonChatQueueConsumer {
    @Resource
    private WebSocketService webSocketService;

    @RabbitListener(queues = RabbitMqConstant.COMMON_FANOUT_CHAT_QUEUE_NAME)
    public void receiveChatMessageQueue(Message<?> message, Channel channel) throws IOException{
        PushMessageDTO pushMessageDTO = (PushMessageDTO) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        assert deliveryTag != null;
        try {
            WsPushTypeEnum wsPushTypeEnum = WsPushTypeEnum.of(pushMessageDTO.getPushType());
            switch (wsPushTypeEnum) {
                case USER:
                    pushMessageDTO.getUserIdList().forEach(userId -> {
                        webSocketService.sendToUserId(pushMessageDTO.getWsBaseVO(), userId);
                    });
                    break;
                case ONLINE_ALL_USER:
                    webSocketService.sendToAllOnline(pushMessageDTO.getWsBaseVO(), null);
                    break;
                default:
                    break;
            }
            channel.basicAck(deliveryTag, false);
            log.info("聊天信息已发送");
        } catch (Exception e) {
            //手动 nack
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
