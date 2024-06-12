package com.qingmeng.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Pair;
import com.qingmeng.config.adapt.ChatAdapt;
import com.qingmeng.config.cache.*;
import com.qingmeng.config.event.MessageRecallEvent;
import com.qingmeng.config.event.MessageSendEvent;
import com.qingmeng.config.netty.vo.WsMsgRecallVO;
import com.qingmeng.config.strategy.message.MessageFactory;
import com.qingmeng.config.strategy.message.MessageStrategy;
import com.qingmeng.config.strategy.messageMark.MessageMarkFactory;
import com.qingmeng.config.strategy.messageMark.MessageMarkStrategy;
import com.qingmeng.dao.*;
import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.dto.chat.*;
import com.qingmeng.dto.chat.msg.MessageExtra;
import com.qingmeng.dto.chat.msg.MsgRecallDTO;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.*;
import com.qingmeng.service.ChatMessageService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.chat.ChatMessageReadVO;
import com.qingmeng.vo.chat.ChatMessageVO;
import com.qingmeng.vo.chat.ChatRoomVO;
import com.qingmeng.vo.chat.MsgReadInfoVO;
import com.qingmeng.vo.chat.child.SessionInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
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
    @Resource
    private ChatMessageDao chatMessageDao;
    @Resource
    private ChatSessionDao chatSessionDao;
    @Resource
    private ChatGroupSettingCache chatGroupSettingCache;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;
    @Resource
    private ChatFriendRoomCache chatFriendRoomCache;
    @Resource
    private UserCache userCache;
    @Resource
    private MessageFactory messageFactory;
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

    /**
     * 撤回消息
     *
     * @param userId           用户 ID
     * @param chatRecallMsgDTO 聊天撤回消息 DTO
     * @author qingmeng
     * @createTime: 2024/06/09 18:38:03
     */
    @Override
    public void recallMsg(Long userId, ChatRecallMsgDTO chatRecallMsgDTO) {
        ChatMessage chatMessage = chatMessageDao.getById(chatRecallMsgDTO.getMsgId());
        // 校验能不能执行撤回
        checkRecall(userId, chatMessage);
        // 执行消息撤回
        recall(userId, chatMessage);
    }

    /**
     * 获取 MSG 阅读信息
     *
     * @param userId                 用户 ID
     * @param chatMessageReadInfoDTO 聊天消息 阅读信息 DTO
     * @return {@link List }<{@link MsgReadInfoVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:02:39
     */
    @Override
    public List<MsgReadInfoVO> getMsgReadInfo(Long userId, ChatMessageReadInfoDTO chatMessageReadInfoDTO) {
        List<ChatMessage> chatMessages = chatMessageDao.listByIds(chatMessageReadInfoDTO.getMsgIds());
        chatMessages.forEach(message -> {
            AssertUtils.equal(userId, message.getFromUserId(), "只能查询自己发送的消息");
        });

        Map<Long, List<ChatMessage>> roomGroup = chatMessages.stream().collect(Collectors.groupingBy(ChatMessage::getRoomId));
        AssertUtils.equal(roomGroup.size(), 1, "只能查相同房间下的消息");
        Long roomId = roomGroup.keySet().iterator().next();
        Long totalCount = chatSessionDao.getTotalCount(roomId);
        return new ArrayList<>(chatMessages.stream().map(chatMessage -> {
            MsgReadInfoVO readInfoDTO = new MsgReadInfoVO();
            readInfoDTO.setMsgId(chatMessage.getId());
            Long readCount = chatSessionDao.getReadCount(chatMessage);
            readInfoDTO.setReadCount(readCount);
            readInfoDTO.setUnReadCount(totalCount - readCount - 1);
            return readInfoDTO;
        }).collect(Collectors.toMap(MsgReadInfoVO::getMsgId, Function.identity())).values());
    }

    /**
     * 信息阅读
     *
     * @param userId             用户 ID
     * @param chatMessageReadDTO 聊天消息读取 DTO
     * @author qingmeng
     * @createTime: 2024/06/09 19:25:04
     */
    @Override
    public void msgRead(Long userId, ChatMessageReadDTO chatMessageReadDTO) {
        ChatSession chatSession = chatSessionDao.getByRoomId(userId, chatMessageReadDTO.getRoomId());
        if (Objects.nonNull(chatSession)) {
            ChatSession update = new ChatSession();
            update.setId(chatSession.getId());
            update.setReadTime(new Date());
            chatSessionDao.updateById(update);
        } else {
            ChatSession insert = new ChatSession();
            insert.setUserId(userId);
            insert.setRoomId(chatMessageReadDTO.getRoomId());
            insert.setReadTime(new Date());
            chatSessionDao.save(insert);
        }
    }

    /**
     * 消息的已读未读列表
     *
     * @param userId                 用户 ID
     * @param chatMessageReadTypeDTO 聊天消息读取类型 DTO
     * @return {@link CursorPageBaseVO }<{@link ChatMessageReadVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:34:26
     */
    @Override
    public CursorPageBaseVO<ChatMessageReadVO> getReadPage(Long userId, ChatMessageReadTypeDTO chatMessageReadTypeDTO) {
        ChatMessage message = chatMessageDao.getById(chatMessageReadTypeDTO.getMsgId());
        AssertUtils.isNotEmpty(message, "消息id有误");
        AssertUtils.equal(userId, message.getFromUserId(), "只能查看自己的消息");
        CursorPageBaseVO<ChatSession> page;
        if (ReadStatusEnum.READ.getCode().equals(chatMessageReadTypeDTO.getSearchType())) {
            page = chatSessionDao.getReadPage(message, chatMessageReadTypeDTO);
        } else {
            page = chatSessionDao.getUnReadPage(message, chatMessageReadTypeDTO);
        }
        if (CollectionUtil.isEmpty(page.getList())) {
            return CursorPageBaseVO.empty();
        }
        return CursorPageBaseVO.init(page, ChatAdapt.buildReadVO(page.getList()));
    }

    /**
     * 获取 msg 页面
     *
     * @param chatMessageReadDTO 聊天消息读取 DTO
     * @param receiveUid         用户 ID
     * @return {@link CursorPageBaseVO }<{@link ChatMessageVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 20:08:56
     */
    @Override
    public CursorPageBaseVO<ChatMessageVO> getMsgPage(ChatMessage1DTO chatMessageReadDTO, Long receiveUid) {
        //用最后一条消息id，来限制被踢出的人能看见的最大一条消息
        Long lastMsgId = getLastMsgId(chatMessageReadDTO.getRoomId(), receiveUid);
        CursorPageBaseVO<ChatMessage> cursorPage = chatMessageDao.getCursorPage(chatMessageReadDTO.getRoomId(), chatMessageReadDTO, lastMsgId);
        if (cursorPage.isEmpty()) {
            return CursorPageBaseVO.empty();
        }
        return CursorPageBaseVO.init(cursorPage, getBatchMsgVO(cursorPage.getList(), receiveUid));
    }

    /**
     * 获取房间会话列表
     *
     * @param cursorPageBaseDTO 请求
     * @param userId            用户 ID
     * @return {@link CursorPageBaseVO }<{@link ChatRoomVO }>
     * @author qingmeng
     * @createTime: 2024/06/12 14:46:12
     */
    @Override
    public CursorPageBaseVO<ChatRoomVO> getSessionPage(CursorPageBaseDTO cursorPageBaseDTO, Long userId) {
        // 用户基础会话
        CursorPageBaseVO<ChatSession> sessionPage = chatSessionDao.getSessionPage(userId, cursorPageBaseDTO);
        List<Long> baseRoomIds = sessionPage.getList().stream().map(ChatSession::getRoomId).collect(Collectors.toList());
        // 基础会话和热门房间合并
        CursorPageBaseVO<Long> page = CursorPageBaseVO.init(sessionPage, baseRoomIds);
        // 最后组装会话信息（名称，头像，未读数等）
        List<ChatRoomVO> result = buildSessionVO(userId, page.getList());
        return CursorPageBaseVO.init(page, result);
    }

    /**
     * 获取会话详细信息
     *
     * @param userId 用户 ID
     * @param roomId 房间 ID
     * @return {@link ChatRoomVO }
     * @author qingmeng
     * @createTime: 2024/06/12 15:33:46
     */
    @Override
    public ChatRoomVO getSessionDetail(Long userId, Long roomId) {
        ChatRoom room = chatRoomCache.get(roomId);
        AssertUtils.isNotEmpty(room, "房间号有误");
        return buildSessionVO(userId, Collections.singletonList(roomId)).get(0);
    }

    /**
     * 获取会话详细信息
     *
     * @param userId   用户 ID
     * @param friendId 好友 ID
     * @return {@link ChatRoomVO }
     * @author qingmeng
     * @createTime: 2024/06/12 15:37:03
     */
    @Override
    public ChatRoomVO getSessionDetailByFriend(Long userId, Long friendId) {
        String tagKey = CommonUtils.getKeyBySort(Arrays.asList(userId, friendId));
        ChatFriendRoom chatFriendRoom = chatFriendRoomDao.getInfoByKey(tagKey);
        AssertUtils.isNotEmpty(chatFriendRoom, "他不是您的好友");
        return buildSessionVO(userId, Collections.singletonList(chatFriendRoom.getRoomId())).get(0);
    }

    /**
     * 构建会话 vo
     *
     * @param userId  userId
     * @param roomIds 房间 ID
     * @return {@link List }<{@link ChatRoomVO }>
     * @author qingmeng
     * @createTime: 2024/06/12 15:22:53
     */
    @NotNull
    private List<ChatRoomVO> buildSessionVO(Long userId, List<Long> roomIds) {
        // 表情和头像
        Map<Long, SessionInfo> roomBaseInfoMap = getSessionInfoMap(roomIds, userId);
        // 最后一条消息
        List<Long> msgIds = roomBaseInfoMap.values().stream().map(SessionInfo::getLastMsgId).collect(Collectors.toList());
        List<ChatMessage> messages = CollectionUtil.isEmpty(msgIds) ? new ArrayList<>() : chatMessageDao.listByIds(msgIds);
        Map<Long, ChatMessage> msgMap = messages.stream().collect(Collectors.toMap(ChatMessage::getId, Function.identity()));
        Map<Long, SysUser> lastMsgUidMap = userCache.getBatch(messages.stream().map(ChatMessage::getFromUserId).collect(Collectors.toList()));
        // 消息未读数
        Map<Long, Long> unReadCountMap = getUnReadCountMap(userId, roomIds);
        return roomBaseInfoMap.values().stream().map(room -> {
                    ChatRoomVO resp = new ChatRoomVO();
                    SessionInfo sessionInfo = roomBaseInfoMap.get(room.getRoomId());
                    resp.setSessionAvatar(sessionInfo.getSessionAvatar());
                    resp.setRoomId(room.getRoomId());
                    resp.setActiveTime(room.getActiveTime());
                    resp.setRoomType(sessionInfo.getRoomType());
                    resp.setSessionName(sessionInfo.getSessionName());
                    ChatMessage message = msgMap.get(room.getLastMsgId());
                    if (Objects.nonNull(message)) {
                        MessageTypeMethodEnum messageTypeMethodEnum = MessageTypeMethodEnum.get(MessageTypeEnum.of(message.getMessageType()).getStrategyMethod());
                        MessageStrategy strategyWithType = messageFactory.getStrategyWithType(messageTypeMethodEnum.getValue());
                        resp.setText(lastMsgUidMap.get(message.getFromUserId()).getUserName() + ":" + strategyWithType.showSessionMsg(message));
                    }
                    resp.setUnreadCount(unReadCountMap.getOrDefault(room.getRoomId(), 0L));
                    return resp;
                }).sorted(Comparator.comparing(ChatRoomVO::getActiveTime).reversed())
                .collect(Collectors.toList());
    }

    private Map<Long, Long> getUnReadCountMap(Long userId, List<Long> roomIds) {
        if (Objects.isNull(userId)) {
            return new HashMap<>();
        }
        List<ChatSession> chatSessionList = chatSessionDao.getByRoomIds(roomIds, userId);
        return chatSessionList.parallelStream()
                .map(session -> Pair.of(session.getRoomId(), chatMessageDao.getUnReadCount(session.getRoomId(), session.getReadTime())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    /**
     * 获取会话信息图
     *
     * @param roomIds 房间 ID
     * @param userId  用户 ID
     * @return {@link Map }<{@link Long }, {@link SessionInfo }>
     * @author qingmeng
     * @createTime: 2024/06/12 15:22:15
     */
    private Map<Long, SessionInfo> getSessionInfoMap(List<Long> roomIds, Long userId) {
        Map<Long, ChatRoom> roomMap = chatRoomCache.getBatch(roomIds);
        // 房间根据好友和群组类型分组
        Map<Integer, List<Long>> groupRoomIdMap = roomMap.values().stream().collect(
                Collectors.groupingBy(
                        ChatRoom::getRoomType,
                        Collectors.mapping(ChatRoom::getId, Collectors.toList())
                )
        );
        // 获取群组信息
        List<Long> groupRoomId = groupRoomIdMap.get(RoomTypeEnum.GROUP.getCode());
        Map<Long, ChatGroupRoom> roomInfoBatch = chatGroupRoomCache.getBatch(groupRoomId);
        // 获取好友信息
        List<Long> friendRoomId = groupRoomIdMap.get(RoomTypeEnum.FRIEND.getCode());
        Map<Long, SysUser> friendRoomMap = getFriendRoomMap(friendRoomId, userId);

        return roomMap.values().stream().map(room -> {
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setRoomId(room.getId());
            sessionInfo.setRoomType(room.getRoomType());
            sessionInfo.setLastMsgId(room.getLastMsgId());
            sessionInfo.setActiveTime(room.getActiveTime());
            if (RoomTypeEnum.of(room.getRoomType()) == RoomTypeEnum.GROUP) {
                ChatGroupRoom roomGroup = roomInfoBatch.get(room.getId());
                ChatGroupSetting chatGroupSetting = chatGroupSettingCache.get(roomGroup.getRoomId());
                sessionInfo.setSessionName(chatGroupSetting.getGroupRoomName());
                sessionInfo.setSessionAvatar(chatGroupSetting.getGroupRoomAvatar());
            } else if (RoomTypeEnum.of(room.getRoomType()) == RoomTypeEnum.FRIEND) {
                SysUser user = friendRoomMap.get(room.getId());
                SysUserFriendSetting friendSetting = userFriendSettingCache.get(CommonUtils.getFriendSettingCacheKey(userId, user.getId()));
                sessionInfo.setSessionName(Objects.nonNull(friendSetting.getNickName()) ? friendSetting.getNickName() : user.getUserName());
                sessionInfo.setSessionAvatar(user.getUserAvatar());
            }
            return sessionInfo;
        }).collect(Collectors.toMap(SessionInfo::getRoomId, Function.identity()));
    }

    private Map<Long, SysUser> getFriendRoomMap(List<Long> roomIds, Long userId) {
        if (CollectionUtil.isEmpty(roomIds)) {
            return new HashMap<>();
        }
        Map<Long, ChatFriendRoom> roomFriendMap = chatFriendRoomCache.getBatch(roomIds);
        Set<Long> friendUidSet = ChatAdapt.getFriendUserIdSet(roomFriendMap.values(), userId);
        Map<Long, SysUser> userBatch = userCache.getBatch(new ArrayList<>(friendUidSet));
        return roomFriendMap.values()
                .stream()
                .collect(Collectors.toMap(ChatFriendRoom::getRoomId, roomFriend -> {
                    Long friendUid = ChatAdapt.getFriendUserId(roomFriend, userId);
                    return userBatch.get(friendUid);
                }));
    }

    /**
     * 获取最后消息 ID
     *
     * @param roomId     房间 ID
     * @param receiveUid 接收 UID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/09 20:09:49
     */
    private Long getLastMsgId(Long roomId, Long receiveUid) {
        ChatRoom room = chatRoomCache.get(roomId);
        AssertUtils.isNotEmpty(room, "房间号有误");
        ChatSession chatSession = chatSessionDao.getByUserIdAndRoomId(receiveUid, roomId);
        return chatSession.getLastMsgId();
    }

    /**
     * 撤回
     *
     * @param recallUserId 调用用户 ID
     * @param message      消息
     * @author qingmeng
     * @createTime: 2024/06/09 18:57:08
     */
    private void recall(Long recallUserId, ChatMessage message) {
        MessageExtra extra = message.getExtra();
        extra.setMsgRecallDTO(new MsgRecallDTO(recallUserId, new Date()));
        ChatMessage update = new ChatMessage();
        update.setId(message.getId());
        update.setMessageType(MessageTypeEnum.RECALL.getType());
        update.setExtra(extra);
        chatMessageDao.updateById(update);
        applicationEventPublisher.publishEvent(new MessageRecallEvent(this, new WsMsgRecallVO(message.getId(), message.getRoomId(), recallUserId)));
    }


    /**
     * 检查撤回
     *
     * @param userId      用户 ID
     * @param chatMessage 聊天消息
     * @author qingmeng
     * @createTime: 2024/06/09 18:39:34
     */
    private void checkRecall(Long userId, ChatMessage chatMessage) {
        AssertUtils.isNotEmpty(chatMessage, "消息有误");
        AssertUtils.notEqual(chatMessage.getMessageType(), MessageTypeEnum.RECALL.getType(), "消息无法撤回");
        // todo 校验权限
        //boolean hasPower = iRoleService.hasPower(userId, GroupRoleEnum.GROUP_OWNER);
        //if (hasPower) {
        //    return;
        //}
        boolean self = Objects.equals(userId, chatMessage.getFromUserId());
        AssertUtils.isTrue(self, "抱歉,您没有权限");
        long between = DateUtil.between(chatMessage.getCreateTime(), new Date(), DateUnit.MINUTE);
        AssertUtils.isTrue(between < 2, "超过2分钟的消息");
    }


    /**
     * 获取批量 msg vo
     *
     * @param chatMessages 聊天消息
     * @param receiveUid   接收 UID
     * @return {@link List }<{@link ChatMessageVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 18:39:29
     */
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
        return ChatAdapt.buildBatchMsgVO(chatMessages, msgMark, receiveUid, msgMap);
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

