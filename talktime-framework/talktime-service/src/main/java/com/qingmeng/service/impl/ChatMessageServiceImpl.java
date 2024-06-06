package com.qingmeng.service.impl;

import com.qingmeng.config.cache.ChatGroupRoomCache;
import com.qingmeng.config.cache.ChatRoomCache;
import com.qingmeng.config.event.MessageSendEvent;
import com.qingmeng.config.strategy.message.MessageStrategy;
import com.qingmeng.config.strategy.message.MessageTypeFactory;
import com.qingmeng.dao.ChatFriendRoomDao;
import com.qingmeng.dao.ChatGroupMemberDao;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.RoomStatusEnum;
import com.qingmeng.enums.chat.RoomTypeEnum;
import com.qingmeng.service.ChatMessageService;
import com.qingmeng.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
    private MessageTypeFactory messageTypeFactory;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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
        MessageStrategy strategyWithType = messageTypeFactory.getStrategyWithType(chatMessageDTO.getMessageType());
        ChatMessage chatMessage = strategyWithType.saveMessage(chatMessageDTO, userId);
        strategyWithType.saveExtraMessage(chatMessage,chatMessageDTO);
        Long msgId = chatMessage.getId();
        applicationEventPublisher.publishEvent(new MessageSendEvent(this, msgId));
        return msgId;
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
            ChatGroupMember member = chatGroupMemberDao.getByUserIdAndGroupRoomId(userId,groupRoom.getId());
            AssertUtils.isNotEmpty(member, "您已经被移除该群");
            AssertUtils.equal(RoomStatusEnum.NORMAL.getCode(), groupRoom.getRoomStatus(), "群聊已封禁");
        }
    }
}
