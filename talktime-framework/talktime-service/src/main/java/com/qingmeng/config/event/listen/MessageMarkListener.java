package com.qingmeng.config.event.listen;

import com.qingmeng.config.adapt.WsAdapter;
import com.qingmeng.config.event.MessageMarkEvent;
import com.qingmeng.config.netty.dto.PushMessageDTO;
import com.qingmeng.config.netty.vo.WsBaseVO;
import com.qingmeng.config.netty.vo.WsMsgMarkVO;
import com.qingmeng.config.rabbitmq.producer.RabbitmqProducer;
import com.qingmeng.constant.RabbitMqConstant;
import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.dao.ChatMessageMarkDao;
import com.qingmeng.dto.chat.ChatMessageOtherMarkDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.enums.chat.MessageMarkTypeEnum;
import com.qingmeng.enums.chat.MessageTypeEnum;
import com.qingmeng.service.SysUserArticleService;
import com.qingmeng.utils.IdUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 消息标记侦听器
 *
 * @author qingmeng
 * @date 2024/06/08 14:06:54
 */
@Component
public class MessageMarkListener {
    @Resource
    private ChatMessageMarkDao chatMessageMarkDao;
    @Resource
    private ChatMessageDao chatMessageDao;
    @Resource
    private SysUserArticleService sysUserArticleService;
    @Resource
    private RabbitmqProducer rabbitmqProducer;

    @Async("visibleTaskExecutor")
    @TransactionalEventListener(classes = MessageMarkEvent.class, fallbackExecution = true)
    public void changeMsgType(MessageMarkEvent event) {
        ChatMessageOtherMarkDTO dto = event.getDto();
        ChatMessage msg = chatMessageDao.getById(dto.getMsgId());
        // 普通消息才需要升级
        if (!Objects.equals(msg.getMessageType(), MessageTypeEnum.TEXT.getType())) {
            return;
        }
        // 消息被标记次数
        Long markCount = chatMessageMarkDao.getMarkCount(dto.getMsgId(), dto.getMarkType());
        MessageMarkTypeEnum markTypeEnum = MessageMarkTypeEnum.of(dto.getMarkType());
        if (markCount < markTypeEnum.getRiseNum()) {
            return;
        }
        // 尝试给用户发送一张徽章
        if (MessageMarkTypeEnum.UPVOTE.getCode().equals(dto.getMarkType())) {
            // todo 绑定 被点赞达人 徽章
            sysUserArticleService.itemReceive(msg.getFromUserId(), 1L,msg.getId().toString());
        }
    }

    @Async("visibleTaskExecutor")
    @TransactionalEventListener(classes = MessageMarkEvent.class, fallbackExecution = true)
    public void notifyAll(MessageMarkEvent event) {
        ChatMessageOtherMarkDTO dto = event.getDto();
        Long markCount = chatMessageMarkDao.getMarkCount(dto.getMsgId(), dto.getMarkType());
        WsBaseVO<WsMsgMarkVO> wsBaseVO = WsAdapter.buildMsgMarkSend(dto, markCount);
        PushMessageDTO pushMessageDTO = new PushMessageDTO(wsBaseVO);
        rabbitmqProducer.sendCommonMsg(IdUtils.simpleUUID(),pushMessageDTO, RabbitMqConstant.COMMON_FANOUT_CHAT_QUEUE_NAME);
    }

}
