package com.qingmeng.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.qingmeng.config.adapt.ChatAdapt;
import com.qingmeng.config.cache.*;
import com.qingmeng.config.strategy.message.MessageFactory;
import com.qingmeng.config.strategy.message.MessageStrategy;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.ChatFriendRoomDao;
import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.dao.ChatSessionDao;
import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.MessageTypeEnum;
import com.qingmeng.enums.chat.MessageTypeMethodEnum;
import com.qingmeng.enums.chat.RoomTypeEnum;
import com.qingmeng.service.ChatSessionService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.chat.ChatRoomVO;
import com.qingmeng.vo.chat.child.SessionInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月09日 19:18:00
 */
@Service
public class ChatSessionServiceImpl implements ChatSessionService {
    @Resource
    private ChatSessionDao chatSessionDao;
    @Resource
    private ChatRoomCache chatRoomCache;
    @Resource
    private ChatFriendRoomDao chatFriendRoomDao;
    @Resource
    private ChatGroupSettingCache chatGroupSettingCache;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;
    @Resource
    private UserCache userCache;
    @Resource
    private MessageFactory messageFactory;
    @Resource
    private ChatMessageDao chatMessageDao;
    @Resource
    private ChatFriendRoomCache chatFriendRoomCache;
    @Resource
    private ChatGroupRoomCache chatGroupRoomCache;

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

    private Map<Long, Long> getUnReadCountMap(Long userId, List<Long> roomIds) {
        if (Objects.isNull(userId)) {
            return new HashMap<>(SystemConstant.ZERO_INT);
        }
        List<ChatSession> chatSessionList = chatSessionDao.getByRoomIds(roomIds, userId);
        return chatSessionList.parallelStream()
                .map(session -> Pair.of(session.getRoomId(), chatMessageDao.getUnReadCount(session.getRoomId(), session.getReadTime())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<Long, SysUser> getFriendRoomMap(List<Long> roomIds, Long userId) {
        if (CollectionUtil.isEmpty(roomIds)) {
            return new HashMap<>(SystemConstant.ZERO_INT);
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
}
