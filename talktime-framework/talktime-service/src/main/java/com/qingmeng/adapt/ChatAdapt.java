package com.qingmeng.adapt;

import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.DisplayNameStatusEnum;
import com.qingmeng.enums.chat.MessageTopStatusEnum;
import com.qingmeng.enums.chat.RemindStatusEnum;
import com.qingmeng.enums.chat.RoomStatusEnum;
import com.qingmeng.enums.user.CloseOrOpenStatusEnum;

import java.util.List;
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
    public static ChatGroupManager buildChatGroupManager(Long userId, Long roomGroupId) {
        ChatGroupManager chatGroupManager = new ChatGroupManager();
        chatGroupManager.setRoomGroupId(roomGroupId);
        chatGroupManager.setUserId(userId);
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

}
