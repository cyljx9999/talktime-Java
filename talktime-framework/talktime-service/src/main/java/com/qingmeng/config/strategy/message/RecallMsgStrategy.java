package com.qingmeng.config.strategy.message;

import cn.hutool.core.bean.BeanUtil;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.config.event.MessageRecallEvent;
import com.qingmeng.config.netty.vo.WsMsgRecallVO;
import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.dto.chat.msg.MessageExtra;
import com.qingmeng.dto.chat.msg.MsgRecallDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.chat.MessageTypeEnum;
import com.qingmeng.utils.AssertUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 撤回文本消息
 * @createTime 2024年06月05日 21:39:00
 */
@Component
public class RecallMsgStrategy extends AbstractMessageStrategy{

    @Resource
    private ChatMessageDao chatMessageDao;
    @Resource
    private UserCache userCache;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 检查消息
     *
     * @param messageDTO 消息 DTO
     * @param userId     用户 ID
     * @author qingmeng
     * @createTime: 2024/06/04 21:56:34
     */
    @Override
    public void checkMsg(ChatMessageDTO messageDTO, Long userId) {
        AssertUtils.validateEntity(messageDTO.getBody(),false);
    }

    /**
     * 展示消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/06 10:43:24
     */
    @Override
    public Object showMsg(ChatMessage msg) {
        MsgRecallDTO recall = msg.getExtra().getMsgRecallDTO();
        SysUser userInfo = userCache.get(recall.getRecallUserId());
        if (!Objects.equals(recall.getRecallUserId(), msg.getFromUserId())) {
            return "管理员\"" + userInfo.getUserName() + "\"撤回了一条成员消息";
        }
        return "\"" + userInfo.getUserName() + "\"撤回了一条消息";
    }

    /**
     * 被回复时——展示的消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:24
     */
    @Override
    public Object showReplyMsg(ChatMessage msg) {
        return "消息已被撤回";
    }

    /**
     * 会话列表——展示的消息
     *
     * @param msg 消息
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:14
     */
    @Override
    public String showContactMsg(ChatMessage msg) {
        return "撤回了一条消息";
    }

    /**
     * 保存额外消息
     *
     * @param msg        消息
     * @param messageDTO 消息 DTO
     * @author qingmeng
     * @createTime: 2024/06/04 21:59:46
     */
    @Override
    public void saveExtraMessage(ChatMessage msg, ChatMessageDTO messageDTO) {
        MsgRecallDTO msgRecall = BeanUtil.toBean(messageDTO.getBody(), MsgRecallDTO.class);
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        ChatMessage update = new ChatMessage();
        update.setId(msg.getId());
        update.setExtra(extra);
        extra.setMsgRecallDTO(msgRecall);
        chatMessageDao.updateById(update);
    }

    public void recall(Long recallUserId, ChatMessage message) {
        //todo 消息覆盖问题用版本号解决
        MessageExtra extra = message.getExtra();
        extra.setMsgRecallDTO(new MsgRecallDTO(recallUserId, new Date()));
        ChatMessage update = new ChatMessage();
        update.setId(message.getId());
        update.setMessageType(MessageTypeEnum.RECALL.getType());
        update.setExtra(extra);
        chatMessageDao.updateById(update);
        applicationEventPublisher.publishEvent(new MessageRecallEvent(this, new WsMsgRecallVO(message.getId(), message.getRoomId(), recallUserId)));

    }
}
