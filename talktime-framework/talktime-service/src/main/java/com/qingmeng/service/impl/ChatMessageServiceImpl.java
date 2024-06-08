package com.qingmeng.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.qingmeng.config.adapt.ChatMessageAdapter;
import com.qingmeng.config.cache.ChatGroupRoomCache;
import com.qingmeng.config.cache.ChatRoomCache;
import com.qingmeng.config.event.MessageSendEvent;
import com.qingmeng.config.strategy.message.MessageStrategy;
import com.qingmeng.config.strategy.message.MessageFactory;
import com.qingmeng.config.strategy.messageMark.MessageMarkFactory;
import com.qingmeng.config.strategy.messageMark.MessageMarkStrategy;
import com.qingmeng.dao.ChatFriendRoomDao;
import com.qingmeng.dao.ChatGroupMemberDao;
import com.qingmeng.dao.ChatMessageMarkDao;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.dto.chat.ChatMessageMarkDTO;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.*;
import com.qingmeng.service.ChatMessageService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.vo.chat.ChatMessageVO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月04日 22:08:00
 */
@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Resource
    private ChatRoomCache chatRoomCache;
    @Resource
    private ChatGroupRoomCache chatGroupRoomCache;
    @Resource
    private ChatFriendRoomDao chatFriendRoomDao;
    @Resource
    private ChatGroupMemberDao chatGroupMemberDao;
    @Resource
    private MessageFactory messageTypeFactory;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private ChatMessageMarkDao chatMessageMarkDao;
    @Resource
    private MessageMarkFactory messageMarkFactory;

    /**
     * 发送消息
     *
     * @param chatMessageDTO 聊天消息 DTO
     * @param userId         用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/06 22:40:57
     */
    @Override
    public Long sendMsg(ChatMessageDTO chatMessageDTO, Long userId) {
        preCheck(chatMessageDTO, userId);
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.of(chatMessageDTO.getMessageType());
        MessageStrategy strategyWithType = messageTypeFactory.getStrategyWithType(messageTypeEnum.getStrategyMethod());
        ChatMessage chatMessage = strategyWithType.saveMessage(chatMessageDTO, userId);
        strategyWithType.saveExtraMessage(chatMessage, chatMessageDTO);
        Long msgId = chatMessage.getId();
        applicationEventPublisher.publishEvent(new MessageSendEvent(this, msgId));
        return msgId;
    }

    /**
     * 获取聊天消息 VO
     *
     * @param msgId      消息 ID
     * @param receiveUid 接收 UID
     * @return {@link ChatMessageVO }
     * @author qingmeng
     * @createTime: 2024/06/07 22:53:52
     */
    @Override
    public ChatMessageVO getChatMessageVO(Long msgId, Long receiveUid) {
        return null;
    }

    /**
     * 获取聊天消息 VO
     *
     * @param chatMessage 聊天消息
     * @param receiveUid  接收 UID
     * @return {@link ChatMessageVO }
     * @author qingmeng
     * @createTime: 2024/06/07 22:54:47
     */
    @Override
    public ChatMessageVO getChatMessageVO(ChatMessage chatMessage, Long receiveUid) {
        return CollUtil.getFirst(getBatchMsgVO(Collections.singletonList(chatMessage), receiveUid));
    }

    /**
     * 设置消息标记
     *
     * @param userId             用户 ID
     * @param chatMessageMarkDTO 聊天消息 Mark DTO
     * @author qingmeng
     * @createTime: 2024/06/08 13:27:32
     */
    @Override
    public void setMsgMark(Long userId, ChatMessageMarkDTO chatMessageMarkDTO) {

        MessageMarkTypeEnum messageMarkTypeEnum = MessageMarkTypeEnum.of(chatMessageMarkDTO.getMarkType());
        MessageMarkStrategy strategyWithType = messageMarkFactory.getStrategyWithType(messageMarkTypeEnum.getStrategyMethod());
        switch (MessageMarkActTypeEnum.of(chatMessageMarkDTO.getActType())) {
            case CONFIRM:
                strategyWithType.mark(userId, chatMessageMarkDTO.getMsgId());
                break;
            case CANCEL:
                strategyWithType.cancelMark(userId, chatMessageMarkDTO.getMsgId());
                break;
            default:
                break;
        }
    }

    private List<ChatMessageVO> getBatchMsgVO(List<ChatMessage> chatMessages, Long receiveUid) {
        if (CollectionUtil.isEmpty(chatMessages)) {
            return new ArrayList<>();
        }
        //查询消息标志
        List<Long> msgIds = chatMessages.stream().map(ChatMessage::getId).collect(Collectors.toList());
        List<ChatMessageMark> msgMark = chatMessageMarkDao.getValidMarkByMsgIdBatch(msgIds);
        Map<Integer, Object> msgMap = new HashMap<>(2);
        chatMessages.forEach(item -> {
            MessageTypeMethodEnum messageTypeMethodEnum = MessageTypeMethodEnum.get(item.getMessageType().toString());
            MessageStrategy strategyWithType = messageTypeFactory.getStrategyWithType(messageTypeMethodEnum.getValue());
            Object object = strategyWithType.showMsg(item);
            msgMap.put(item.getMessageType(), object);
        });
        return ChatMessageAdapter.buildBatchMsgVO(chatMessages, msgMark, receiveUid, msgMap);
    }

    private void preCheck(ChatMessageDTO chatMessageDTO, Long userId) {
        ChatRoom room = chatRoomCache.get(chatMessageDTO.getRoomId());
        if (Objects.equals(room.getRoomType(), RoomTypeEnum.FRIEND.getCode())) {
            ChatFriendRoom roomFriend = chatFriendRoomDao.getByRoomId(chatMessageDTO.getRoomId());
            AssertUtils.equal(RoomStatusEnum.NORMAL.getCode(), roomFriend.getRoomStatus(), "您已经被对方拉黑");
            AssertUtils.isTrue(userId.equals(roomFriend.getUserId()) || userId.equals(roomFriend.getOtherUserId()), "您已经被对方拉黑");
        }
        if (Objects.equals(room.getRoomType(), RoomTypeEnum.GROUP.getCode())) {
            ChatGroupRoom groupRoom = chatGroupRoomCache.get(chatMessageDTO.getRoomId());
            ChatGroupMember member = chatGroupMemberDao.getByUserIdAndGroupRoomId(userId, groupRoom.getId());
            AssertUtils.isNotEmpty(member, "您已经被移除该群");
            AssertUtils.equal(RoomStatusEnum.NORMAL.getCode(), groupRoom.getRoomStatus(), "群聊已封禁");
        }
    }
}
