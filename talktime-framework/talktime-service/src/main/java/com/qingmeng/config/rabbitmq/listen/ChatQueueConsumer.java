package com.qingmeng.config.rabbitmq.listen;


import com.qingmeng.config.cache.ChatGroupManagerCache;
import com.qingmeng.config.cache.ChatRoomCache;
import com.qingmeng.config.rabbitmq.producer.RabbitmqProducer;
import com.qingmeng.constant.RabbitMqConstant;
import com.qingmeng.dao.ChatFriendRoomDao;
import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.dao.ChatRoomDao;
import com.qingmeng.entity.ChatFriendRoom;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.entity.ChatRoom;
import com.qingmeng.enums.chat.RoomTypeEnum;
import com.qingmeng.utils.RedisUtils;
import com.qingmeng.vo.chat.ChatMessageVO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * @author 清梦
 * @version 1.0.0
 * @Description 聊天队列的消费者
 * @createTime 2023年04月18日 00:27:35
 */
@Slf4j
@Component
public class ChatQueueConsumer {
    @Resource
    private RedisUtils redisUtil;
    @Resource
    private ChatMessageDao chatMessageDao;
    @Resource
    private ChatRoomCache chatRoomCache;
    @Resource
    private ChatRoomDao chatRoomDao;
    @Resource
    private ChatGroupManagerCache chatGroupManagerCache;
    @Resource
    private ChatFriendRoomDao chatFriendRoomDao;
    @Resource
    private RabbitmqProducer rabbitmqProducer;

    /**
     * 聊天队列
     *
     * @param message 消息
     * @param channel 渠道
     * @author qingmeng
     * @createTime: 2024/06/06 23:49:45
     */
    @Value("${ali.sms.accessKeyId}")

    @RabbitListener(queues = RabbitMqConstant.FANOUT_CHAT_QUEUE_NAME)
    public void receiveChatMessageQueue(Message<?> message, Channel channel) throws IOException{
        Long msgId = (Long) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        assert deliveryTag != null;
        try {

            ChatMessage chatMessage = chatMessageDao.getById(msgId);
            ChatRoom chatRoom = chatRoomCache.get(chatMessage.getRoomId());
            ChatMessageVO msgResp = chatService.getMsgResp(chatMessage, null);
            // 所有房间更新房间最新消息
            chatRoomDao.refreshActiveTime(chatRoom.getId(), chatMessage.getId(), chatMessage.getCreateTime());
            chatRoomCache.delete(chatRoom.getId());

            List<Long> memberUidList = new ArrayList<>();
            if (Objects.equals(chatRoom.getRoomType(), RoomTypeEnum.GROUP.getCode())) {
                //普通群聊推送所有群成员
                memberUidList = chatGroupManagerCache.getMemberUidList(chatRoom.getId());
            } else if (Objects.equals(chatRoom.getRoomType(), RoomTypeEnum.FRIEND.getCode())) {
                //单聊对象推送
                ChatFriendRoom chatFriendRoom = chatFriendRoomDao.getByRoomId(chatRoom.getId());
                memberUidList = Arrays.asList(chatFriendRoom.getUserId(), chatFriendRoom.getOtherUserId());
            }
            //更新所有群成员的会话时间
            contactDao.refreshOrCreateActiveTime(chatRoom.getId(), memberUidList, chatMessage.getId(), chatMessage.getCreateTime());
            //推送房间成员
            rabbitmqProducer.sendReliableMsgBySimpleMode(WSAdapter.buildMsgSend(msgResp), memberUidList);



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
