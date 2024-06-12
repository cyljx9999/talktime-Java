package com.qingmeng.config.adapt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.qingmeng.config.netty.vo.WsGroupInviteVO;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.*;
import com.qingmeng.enums.common.CloseOrOpenStatusEnum;
import com.qingmeng.enums.common.YesOrNoEnum;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.chat.ChatMessageReadVO;
import com.qingmeng.vo.chat.ChatMessageVO;
import com.qingmeng.vo.chat.GroupDetailInfoVO;
import com.qingmeng.vo.chat.child.Message;
import com.qingmeng.vo.chat.child.MessageMark;
import com.qingmeng.vo.user.ManagerInfo;
import com.qingmeng.vo.user.SimpleUserInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月06日 22:15:00
 */
public class ChatAdapt {

    /**
     * 建立聊天群房间
     *
     * @param roomId 房间 ID
     * @return {@link ChatGroupRoom }
     * @author qingmeng
     * @createTime: 2023/12/06 22:19:08
     */
    public static ChatGroupRoom buildChatGroupRoom(Long roomId) {
        ChatGroupRoom chatGroupRoom = new ChatGroupRoom();
        chatGroupRoom.setRoomId(roomId);
        chatGroupRoom.setRoomStatus(RoomStatusEnum.NORMAL.getCode());
        return chatGroupRoom;
    }

    /**
     * 建立保存聊天群成员列表
     *
     * @param groupRoomId 组会议室 ID
     * @param userIds     用户id集合
     * @return {@link List }<{@link ChatGroupMember }>
     * @author qingmeng
     * @createTime: 2023/12/06 22:22:19
     */
    public static List<ChatGroupMember> buildSaveChatGroupMemberList(Long groupRoomId, List<Long> userIds) {
        return userIds.stream().map(userId -> {
            ChatGroupMember chatGroupMember = new ChatGroupMember();
            chatGroupMember.setGroupRoomId(groupRoomId);
            chatGroupMember.setUserId(userId);
            return chatGroupMember;
        }).collect(Collectors.toList());
    }

    /**
     * 建立聊天群管理
     *
     * @param userId      用户 ID
     * @param roomGroupId 会议室组 ID
     * @return {@link ChatGroupManager }
     * @author qingmeng
     * @createTime: 2023/12/06 22:38:35
     */
    public static ChatGroupManager buildChatGroupOwner(Long userId, Long roomGroupId) {
        ChatGroupManager chatGroupManager = new ChatGroupManager();
        chatGroupManager.setRoomGroupId(roomGroupId);
        chatGroupManager.setUserId(userId);
        chatGroupManager.setRoleType(GroupRoleEnum.GROUP_OWNER.getCode());
        return chatGroupManager;
    }

    /**
     * 建立聊天群个人设置保存列表
     *
     * @param roomGroupId 会议室组 ID
     * @param userIds     用户 ID
     * @return {@link List }<{@link ChatGroupPersonalSetting }>
     * @author qingmeng
     * @createTime: 2023/12/06 22:44:31
     */
    public static List<ChatGroupPersonalSetting> buildChatGroupPersonalSettingSaveList(Long roomGroupId, List<Long> userIds) {
        return userIds.stream().map(userId -> {
            ChatGroupPersonalSetting chatGroupPersonalSetting = new ChatGroupPersonalSetting();
            chatGroupPersonalSetting.setGroupRoomId(roomGroupId);
            chatGroupPersonalSetting.setUserId(userId);
            chatGroupPersonalSetting.setTopStatus(MessageTopStatusEnum.NORMAL.getCode());
            chatGroupPersonalSetting.setDisplayNameStatus(DisplayNameStatusEnum.DISPLAY.getCode());
            chatGroupPersonalSetting.setRemindStatus(RemindStatusEnum.OPEN.getCode());
            return chatGroupPersonalSetting;
        }).collect(Collectors.toList());
    }

    /**
     * 构建默认聊天组设置
     *
     * @param groupRoomId     组会议室 ID
     * @param sysUser         sys 用户
     * @param groupRoomQrcode 团体房二维码
     * @return {@link ChatGroupSetting }
     * @author qingmeng
     * @createTime: 2023/12/06 22:51:18
     */
    public static ChatGroupSetting buildDefaultChatGroupSetting(Long groupRoomId, SysUser sysUser, String groupRoomQrcode) {
        ChatGroupSetting chatGroupSetting = new ChatGroupSetting();
        chatGroupSetting.setGroupRoomId(groupRoomId);
        chatGroupSetting.setGroupRoomName("由" + sysUser.getUserName() + "发起的群聊");
        chatGroupSetting.setGroupRoomAvatar(sysUser.getUserAvatar());
        chatGroupSetting.setGroupRoomQrcode(groupRoomQrcode);
        chatGroupSetting.setInvitationConfirmation(CloseOrOpenStatusEnum.CLOSE.getCode());
        return chatGroupSetting;
    }

    /**
     * 建立邀请组列表
     *
     * @param ids              IDS
     * @param chatGroupSetting 聊天组设置
     * @param userName         用户名
     * @return {@link List }<{@link WsGroupInviteVO }>
     * @author qingmeng
     * @createTime: 2023/12/07 09:20:58
     */
    public static List<WsGroupInviteVO> buildInviteGroupList(List<Long> ids, ChatGroupSetting chatGroupSetting, String userName) {
        // todo
        return new ArrayList<>();
    }

    /**
     * 建立保存聊天组成员
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @return {@link ChatGroupMember }
     * @author qingmeng
     * @createTime: 2023/12/08 08:51:00
     */
    public static ChatGroupMember buildSaveChatGroupMember(Long userId, Long groupRoomId) {
        return buildSaveChatGroupMemberList(userId, Collections.singletonList(groupRoomId)).get(0);
    }

    /**
     * 建立聊天组设置
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @return {@link ChatGroupPersonalSetting }
     * @author qingmeng
     * @createTime: 2023/12/08 08:50:58
     */
    public static ChatGroupPersonalSetting buildChatGroupSetting(Long userId, Long groupRoomId) {
        return buildChatGroupPersonalSettingSaveList(userId, Collections.singletonList(groupRoomId)).get(0);
    }

    /**
     * 建立聊天群管理列表
     *
     * @param groupRoomId 组会议室 ID
     * @param ids         IDS
     * @return {@link List }<{@link ChatGroupManager }>
     * @author qingmeng
     * @createTime: 2023/12/08 11:32:59
     */
    public static List<ChatGroupManager> buildChatGroupManagementList(Long groupRoomId, List<Long> ids) {
        return ids.stream().map(id -> {
            ChatGroupManager chatGroupManager = new ChatGroupManager();
            chatGroupManager.setRoomGroupId(groupRoomId);
            chatGroupManager.setUserId(id);
            chatGroupManager.setRoleType(GroupRoleEnum.GROUP_MANAGEMENT.getCode());
            return chatGroupManager;
        }).collect(Collectors.toList());
    }

    /**
     * 构造 群聊详细信息
     *
     * @param userId                      用户 ID
     * @param memberIds                   会员 ID
     * @param friendIds                   好友ID
     * @param chatGroupSetting            聊天组设置
     * @param chatGroupPersonalSetting    聊天组个人设置
     * @param friendSettingList           好友设置列表
     * @param userMap                     用户集合
     * @param chatGroupPersonalSettingMap 聊天群个人设置集合
     * @return {@link GroupDetailInfoVO }
     * @author qingmeng
     * @createTime: 2023/12/09 14:55:20
     */
    public static GroupDetailInfoVO buildGroupDetailInfo(Long userId,
                                                         List<Long> memberIds,
                                                         List<Long> friendIds,
                                                         ChatGroupSetting chatGroupSetting,
                                                         ChatGroupPersonalSetting chatGroupPersonalSetting,
                                                         List<SysUserFriendSetting> friendSettingList,
                                                         Map<Long, SysUser> userMap,
                                                         Map<String, ChatGroupPersonalSetting> chatGroupPersonalSettingMap,
                                                         List<ChatGroupManager> managerAllList) {
        GroupDetailInfoVO groupDetailInfo = new GroupDetailInfoVO();
        groupDetailInfo.setGroupRoomName(chatGroupSetting.getGroupRoomName());
        groupDetailInfo.setGroupRoomAvatar(chatGroupSetting.getGroupRoomAvatar());
        groupDetailInfo.setGroupNotice(chatGroupSetting.getGroupNotice());
        groupDetailInfo.setInvitationConfirmation(chatGroupSetting.getInvitationConfirmation());
        groupDetailInfo.setGroupRoomQrCode(chatGroupSetting.getGroupRoomQrcode());
        groupDetailInfo.setTopStatus(chatGroupPersonalSetting.getTopStatus());
        groupDetailInfo.setDisplayNameStatus(chatGroupPersonalSetting.getDisplayNameStatus());
        groupDetailInfo.setNickName(chatGroupPersonalSetting.getNickName());
        groupDetailInfo.setRemindStatus(chatGroupPersonalSetting.getRemindStatus());
        Map<String, List<?>> map = getMemberListAndManagerList(userId, memberIds, friendIds, friendSettingList, userMap, chatGroupPersonalSettingMap, managerAllList);
        groupDetailInfo.setMemberList((List<SimpleUserInfo>) map.get("memberList"));
        groupDetailInfo.setManagerList((List<ManagerInfo>) map.get("managerInfoList"));
        return groupDetailInfo;
    }

    /**
     * 获取成员列表
     *
     * @param userId                      用户 ID
     * @param memberIds                   会员 ID
     * @param friendIds                   好友ID
     * @param friendSettingList           好友设置列表
     * @param userMap                     用户集合
     * @param chatGroupPersonalSettingMap 聊天群个人设置集合
     * @param managerAllList              管理员 列表
     * @return {@link Map }<{@link String }, {@link List }<{@link ? }>>
     * @author qingmeng
     * @createTime: 2023/12/11 11:06:48
     */
    private static Map<String, List<?>> getMemberListAndManagerList(Long userId,
                                                                    List<Long> memberIds,
                                                                    List<Long> friendIds,
                                                                    List<SysUserFriendSetting> friendSettingList,
                                                                    Map<Long, SysUser> userMap,
                                                                    Map<String, ChatGroupPersonalSetting> chatGroupPersonalSettingMap,
                                                                    List<ChatGroupManager> managerAllList) {
        Map<String, List<?>> map = new HashMap<>(2);
        List<SimpleUserInfo> memberList = new ArrayList<>();
        List<ManagerInfo> managerInfoList = new ArrayList<>();
        memberIds.forEach(id -> {
            SimpleUserInfo userInfo = new SimpleUserInfo();
            boolean isFriend = friendIds.contains(id);
            if (isFriend) {
                // 获取 当前用户 对 对方 的设置数据，判断是否进行备注
                SysUserFriendSetting friendSetting = friendSettingList.stream()
                        .filter(setting -> {
                            // 通过tagKey和userId定位数据 tagKey:1-2 userId:1 表示用户1对用户2的设置
                            boolean flagA = setting.getTagKey().equals(CommonUtils.getKeyBySort(Arrays.asList(userId, id)));
                            boolean flagB = setting.getUserId().equals(userId);
                            return flagA && flagB;
                        })
                        .findAny()
                        .orElse(new SysUserFriendSetting());
                SysUser sysUser = userMap.get(id);
                userInfo.setUserAvatar(sysUser.getUserAvatar());
                // 如果有备注则采用备注，否则则使用对方原本的用户名
                userInfo.setUserName(StrUtil.isNotBlank(friendSetting.getNickName()) ? friendSetting.getNickName() : sysUser.getUserName());
            } else {
                SysUser sysUser = userMap.get(id);
                userInfo.setUserAvatar(sysUser.getUserAvatar());
                ChatGroupPersonalSetting chatGroupPersonalSetting = chatGroupPersonalSettingMap.get("");
                // 如果有群昵称则采用群昵称，否则则使用对方原本的用户名
                userInfo.setUserName(StrUtil.isNotBlank(chatGroupPersonalSetting.getNickName()) ? chatGroupPersonalSetting.getNickName() : sysUser.getUserName());
            }
            ChatGroupManager chatGroupManager = managerAllList.stream().filter(manager -> manager.getUserId().equals(id)).findAny().orElse(new ChatGroupManager());
            if (Objects.nonNull(chatGroupManager.getId())) {
                ManagerInfo managerInfo = new ManagerInfo();
                managerInfo.setUserAvatar(userInfo.getUserAvatar());
                managerInfo.setUserName(userInfo.getUserName());
                Integer roleType = chatGroupManager.getRoleType();
                String roleName = GroupRoleEnum.GROUP_OWNER.getCode().equals(roleType) ? "群主" : "管理员";
                managerInfo.setRoleName(roleName);
                managerInfoList.add(managerInfo);
            }
            memberList.add(userInfo);
        });
        map.put("memberList", memberList);
        map.put("managerInfoList", managerInfoList);
        return map;
    }

    public static ChatMessage buildChatMessageSave(ChatMessageDTO chatMessageDTO, Long userId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(chatMessageDTO.getRoomId());
        chatMessage.setFromUserId(userId);
        chatMessage.setStatus(0);
        chatMessage.setMessageType(chatMessageDTO.getMessageType());
        return chatMessage;
    }


    /**
     * 构建批处理 msg vo
     *
     * @param chatMessages 聊天消息
     * @param msgMark      消息标志
     * @param receiveUid   接收 UID
     * @param msgMap       展示消息集合
     * @return {@link List }<{@link ChatMessageVO }>
     * @author qingmeng
     * @createTime: 2024/06/07 23:42:17
     */
    public static List<ChatMessageVO> buildBatchMsgVO(List<ChatMessage> chatMessages, List<ChatMessageMark> msgMark, Long receiveUid,Map<Integer, Object> msgMap) {
        Map<Long, List<ChatMessageMark>> markMap = msgMark.stream().collect(Collectors.groupingBy(ChatMessageMark::getMsgId));
        return chatMessages.stream().map(chatMessage -> {
                    ChatMessageVO chatMessageVO = new ChatMessageVO();
                    chatMessageVO.setFromUserId(chatMessage.getFromUserId());
                    chatMessageVO.setChatMessage(buildMessage(chatMessage, markMap.getOrDefault(chatMessage.getId(), new ArrayList<>()), receiveUid,msgMap.get(chatMessage.getMessageType())));
                    return chatMessageVO;
                })
                .sorted(Comparator.comparing(a -> a.getChatMessage().getSendTime()))
                .collect(Collectors.toList());
    }

    private static Message buildMessage(ChatMessage chatMessage, List<ChatMessageMark> marks, Long receiveUid, Object showMsg) {
        Message messageVO = new Message();
        BeanUtil.copyProperties(chatMessage, messageVO);
        messageVO.setSendTime(chatMessage.getCreateTime());
        messageVO.setBody(showMsg);
        //消息标记
        messageVO.setMessageMark(buildMsgMark(marks, receiveUid));
        return messageVO;
    }

    private static MessageMark buildMsgMark(List<ChatMessageMark> chatMessageMarks, Long receiveUid) {
        Map<Integer, List<ChatMessageMark>> typeMap = chatMessageMarks.stream().collect(Collectors.groupingBy(ChatMessageMark::getType));
        List<ChatMessageMark> likeMarks = typeMap.getOrDefault(MessageMarkTypeEnum.UPVOTE.getCode(), new ArrayList<>());
        List<ChatMessageMark> dislikeMarks = typeMap.getOrDefault(MessageMarkTypeEnum.REPORT.getCode(), new ArrayList<>());
        MessageMark mark = new MessageMark();
        mark.setLikeCount(likeMarks.size());
        mark.setUserLike(
                Optional.ofNullable(receiveUid)
                        .filter(userId -> likeMarks.stream().anyMatch(a -> Objects.equals(a.getUserId(), userId)))
                        .map(a -> YesOrNoEnum.YES.getCode())
                        .orElse(YesOrNoEnum.NO.getCode())
        );
        mark.setDislikeCount(dislikeMarks.size());
        mark.setUserDislike(
                Optional.ofNullable(receiveUid)
                        .filter(userId -> dislikeMarks.stream().anyMatch(a -> Objects.equals(a.getUserId(), userId)))
                        .map(a -> YesOrNoEnum.YES.getCode())
                        .orElse(YesOrNoEnum.NO.getCode())
        );
        return mark;
    }

    /**
     * 获取聊天消息标记
     *
     * @param id     同上
     * @param userId 用户 ID
     * @param type   类型
     * @param msgId  消息 ID
     * @param status 状态
     * @return {@link ChatMessageMark }
     * @author qingmeng
     * @createTime: 2024/06/08 13:44:10
     */
    public static ChatMessageMark getChatMessageMark(Long id,Long userId,Integer type,Long msgId,Integer status) {
        ChatMessageMark chatMessageMark = new ChatMessageMark();
        chatMessageMark.setId(id);
        chatMessageMark.setMsgId(msgId);
        chatMessageMark.setUserId(userId);
        chatMessageMark.setType(type);
        chatMessageMark.setStatus(status);
        return chatMessageMark;
    }


    /**
     * 构建 READ VO
     *
     * @param list 列表
     * @return {@link List }<{@link ChatMessageReadVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:58:53
     */
    public static List<ChatMessageReadVO> buildReadVO(List<ChatSession> list) {
        return list.stream().map(chatSession -> {
            ChatMessageReadVO resp = new ChatMessageReadVO();
            resp.setUserId(chatSession.getUserId());
            return resp;
        }).collect(Collectors.toList());
    }

    public static Set<Long> getFriendUserIdSet(Collection<ChatFriendRoom> values, Long userId) {
        return values.stream()
                .map(a -> getFriendUserId(a, userId))
                .collect(Collectors.toSet());
    }


    /**
     * 获取好友用户 ID
     *
     * @param chatFriendRoom 聊天 好友室
     * @param userId         用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/12 15:18:59
     */
    public static Long getFriendUserId(ChatFriendRoom chatFriendRoom, Long userId) {
        return Objects.equals(userId, chatFriendRoom.getUserId()) ? chatFriendRoom.getOtherUserId() : chatFriendRoom.getUserId();
    }
}
