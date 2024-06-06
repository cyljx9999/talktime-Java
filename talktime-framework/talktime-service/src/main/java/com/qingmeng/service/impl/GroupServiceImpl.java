package com.qingmeng.service.impl;

import com.qingmeng.config.adapt.ChatAdapt;
import com.qingmeng.config.adapt.RoomAdapt;
import com.qingmeng.config.annotation.RedissonLock;
import com.qingmeng.config.cache.*;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.*;
import com.qingmeng.dto.chatGroup.*;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.RoomStatusEnum;
import com.qingmeng.enums.chat.RoomTypeEnum;
import com.qingmeng.service.FileService;
import com.qingmeng.service.GroupService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.chat.GroupDetailInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月06日 22:03:00
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private ChatRoomDao chatRoomDao;
    @Resource
    private ChatGroupMemberDao chatGroupMemberDao;
    @Resource
    private ChatGroupManagerDao chatGroupManagerDao;
    @Resource
    private ChatGroupRoomDao chatGroupRoomDao;
    @Resource
    private ChatGroupPersonalSettingDao chatGroupPersonalSettingDao;
    @Resource
    private ChatGroupSettingDao chatGroupSettingDao;
    @Resource
    private UserCache userCache;
    @Resource
    private FileService fileService;
    @Resource
    private SysUserFriendDao sysUserFriendDao;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;
    @Resource
    private ChatGroupMemberCache chatGroupMemberCache;
    @Resource
    private ChatRoomCache chatRoomCache;
    @Resource
    private ChatGroupRoomCache chatGroupRoomCache;
    @Resource
    private ChatGroupSettingCache chatGroupSettingCache;
    @Resource
    private ChatGroupPersonalSettingCache chatGroupPersonalSettingCache;
    @Resource
    private ChatGroupManagerCache chatGroupManagerCache;


    /**
     * 创建群聊
     *
     * @param userId        用户 ID
     * @param creatGroupDTO 创建群聊 DTO
     * @author qingmeng
     * @createTime: 2023/12/06 22:07:59
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void creatGroup(Long userId, CreatGroupDTO creatGroupDTO) {
        List<Long> ids = preCheckUserList(creatGroupDTO.getMemberIds(), SystemConstant.CREATE_GROUP_MIN_COUNT);
        // 创建抽象群聊房间记录
        ChatRoom chatRoom = RoomAdapt.buildDefaultGroupRoom();
        chatRoomDao.save(chatRoom);
        Long roomId = chatRoom.getId();
        // 创建群聊房间记录
        ChatGroupRoom chatGroupRoom = ChatAdapt.buildChatGroupRoom(roomId);
        chatGroupRoomDao.save(chatGroupRoom);
        Long groupRoomId = chatGroupRoom.getId();
        // 添加群成员记录
        addGroupMemberRecord(groupRoomId, ids);
        // 添加群管理员(即群主)记录
        ChatGroupManager chatGroupManager = ChatAdapt.buildChatGroupOwner(userId, groupRoomId);
        chatGroupManagerDao.save(chatGroupManager);
        // 添加对应成员对群聊设置记录
        List<ChatGroupPersonalSetting> builtChatGroupPersonalSettingSaveList = ChatAdapt.buildChatGroupPersonalSettingSaveList(groupRoomId, ids);
        chatGroupPersonalSettingDao.saveBatch(builtChatGroupPersonalSettingSaveList);
        // 添加管理员管理群聊设置记录
        SysUser sysUser = userCache.get(userId);
        String qrcodeUrl = fileService.getQrcodeUrl(groupRoomId);
        ChatGroupSetting chatGroupPersonalSetting = ChatAdapt.buildDefaultChatGroupSetting(groupRoomId, sysUser, qrcodeUrl);
        chatGroupSettingDao.save(chatGroupPersonalSetting);
    }


    /**
     * 邀请
     *
     * @param userId    用户 ID
     * @param inviteDTO 邀请 DTO
     * @author qingmeng
     * @createTime: 2023/12/07 08:42:51
     */
    @Override
    @RedissonLock(key = "#invite", waitTime = 3000)
    public void invite(Long userId, InviteDTO inviteDTO) {
        Long roomId = inviteDTO.getRoomId();
        checkChatRoom(roomId);
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        Long groupRoomId = chatGroupRoom.getId();
        checkGroupRoom(groupRoomId);
        List<Long> inviteIds = preCheckUserList(inviteDTO.getUserIds(), SystemConstant.INVITE_MEMBER_MIN_COUNT);
        int memberCount = chatGroupMemberCache.getMemberUserIdList(roomId).size();
        AssertUtils.equal(memberCount + inviteIds.size(), SystemConstant.MEMBER_MAX_COUNT, "邀请人数已超过限制");
        if (memberCount > SystemConstant.INVITE_REMIND_COUNT) {
            SysUser sysUser = userCache.get(userId);
            ChatGroupSetting chatGroupSetting = chatGroupSettingCache.get(roomId);
            // todo 发送邀请加群通知
        } else {
            // 直接添加群成员记录
            addGroupMemberRecord(groupRoomId, inviteIds);
        }
    }

    /**
     * 接受邀请
     *
     * @param userId 用户 ID
     * @param roomId id
     * @author qingmeng
     * @createTime: 2023/12/10 11:28:29
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptInvite(Long userId, Long roomId) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        Long groupRoomId = chatGroupRoom.getId();
        checkGroupRoom(roomId);
        checkInGroup(userId, roomId);
        // todo checkTime()
        // 添加群成员记录
        ChatGroupMember saveChatGroupMember = ChatAdapt.buildSaveChatGroupMember(userId, groupRoomId);
        chatGroupMemberDao.save(saveChatGroupMember);
        // 添加个人群聊设置
        ChatGroupPersonalSetting chatGroupPersonalSetting = ChatAdapt.buildChatGroupSetting(userId, groupRoomId);
        chatGroupPersonalSettingDao.save(chatGroupPersonalSetting);
        // todo 推送信息
    }

    /**
     * 踢出
     *
     * @param userId     用户 ID
     * @param kickOutDTO 踢出 DTO
     * @author qingmeng
     * @createTime: 2023/12/10 11:48:53
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void kickOut(Long userId, KickOutDTO kickOutDTO) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(kickOutDTO.getRoomId());
        Long groupRoomId = chatGroupRoom.getId();
        checkGroupRoom(kickOutDTO.getRoomId());
        checkNotInGroup(kickOutDTO.getUserId(), kickOutDTO.getRoomId());
        chatGroupMemberDao.removeMember(kickOutDTO.getUserId(), groupRoomId);
        chatGroupPersonalSettingDao.removeSetting(kickOutDTO.getUserId(), groupRoomId);
        // 删除相关缓存
        chatGroupMemberCache.evictMemberUidList(kickOutDTO.getRoomId());
        chatGroupPersonalSettingCache.delete(groupRoomId + ":" + userId);
    }

    /**
     * 更改设置
     *
     * @param alterGroupSettingDTO 更改组设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 10:32:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterSetting(AlterGroupSettingDTO alterGroupSettingDTO) {
        checkGroupRoom(alterGroupSettingDTO.getRoomId());
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(alterGroupSettingDTO.getRoomId());
        chatGroupSettingDao.updateSetting(alterGroupSettingDTO, chatGroupRoom.getId());
        chatGroupSettingCache.delete(alterGroupSettingDTO.getRoomId());
    }

    /**
     * 添加管理
     *
     * @param addManagementDTO 添加管理 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 10:39:57
     */
    @Override
    public void addManagement(AddManagementDTO addManagementDTO) {
        Long roomId = addManagementDTO.getRoomId();
        // 检查群组ID是否有效
        checkGroupRoom(roomId);
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        // 检查用户ID是否有效
        List<Long> ids = preCheckUserList(addManagementDTO.getUserIds(), SystemConstant.ADD_MANAGEMENT_MIN_COUNT);
        //  检查用户是否已经在管理员中
        List<Long> managementIds = chatGroupManagerCache.getManagerIdList(roomId);
        List<Long> saveIds = checkManagement(ids, managementIds);
        // 检查群管理员数量是否超过最大值
        AssertUtils.checkGreaterThan((long) (managementIds.size() + saveIds.size()), SystemConstant.MANAGEMENT_MAX_COUNT, "群管理员最多可能添加" + SystemConstant.MANAGEMENT_MAX_COUNT + "个");
        // 根据群组ID和用户ID构建群管理员列表
        List<ChatGroupManager> chatGroupManagers = ChatAdapt.buildChatGroupManagementList(chatGroupRoom.getId(), saveIds);
        // 保存群管理员列表
        chatGroupManagerDao.saveBatch(chatGroupManagers);
        chatGroupManagerCache.evictManagerIdList(roomId);
    }

    /**
     * 删除管理
     *
     * @param removeManagementDTO 删除管理 DTO
     * @author qingmeng
     * @createTime: 2023/12/09 13:31:55
     */
    @Override
    public void removeManagement(RemoveManagementDTO removeManagementDTO) {
        Long roomId = removeManagementDTO.getRoomId();
        checkGroupRoom(roomId);
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        // 检查群组ID是否有效
        checkGroupRoom(roomId);
        chatGroupManagerDao.removeManagement(chatGroupRoom.getId(), removeManagementDTO.getUserId());
        chatGroupManagerCache.evictManagerIdList(roomId);
    }

    /**
     * 更改人员设置
     *
     * @param userId                       用户 ID
     * @param alterGroupPersonalSettingDTO 修改个人设置参数
     * @author qingmeng
     * @createTime: 2023/12/09 13:38:55
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterPersonSetting(Long userId, AlterGroupPersonalSettingDTO alterGroupPersonalSettingDTO) {
        // 检查群组ID是否有效
        Long roomId = alterGroupPersonalSettingDTO.getRoomId();
        checkGroupRoom(roomId);
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        //  检查用户是否在群聊里
        Long groupRoomId = chatGroupRoom.getId();
        checkNotInGroup(userId, roomId);
        chatGroupPersonalSettingDao.alterPersonSetting(userId, groupRoomId, alterGroupPersonalSettingDTO);
        chatGroupPersonalSettingCache.delete(groupRoomId + ":" + userId);
    }

    /**
     * 获取组详细信息
     *
     * @param userId 用户 ID
     * @param roomId 房间 ID
     * @return {@link GroupDetailInfoVO }
     * @author qingmeng
     * @createTime: 2023/12/09 13:59:21
     */
    @Override
    public GroupDetailInfoVO getGroupDetailInfo(Long userId, Long roomId) {
        checkGroupRoom(roomId);
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        Long groupRoomId = chatGroupRoom.getId();
        // 获取群聊设置
        ChatGroupSetting chatGroupSetting = chatGroupSettingCache.get(roomId);
        // 获取个人的群聊设置
        ChatGroupPersonalSetting chatGroupPersonalSetting = chatGroupPersonalSettingCache.get(groupRoomId + ":" + userId);
        // 获取当前群聊成员的ids
        List<Long> memberIds = chatGroupMemberCache.getMemberUserIdList(roomId);
        // 获取当前用户的所有好友id列表
        List<Long> addFriendIds = sysUserFriendDao.getFriendListById(userId)
                .stream().distinct()
                .map(SysUserFriend::getFriendId)
                .collect(Collectors.toList());
        // 获取当前群聊中的好友id
        List<Long> friendIds = getFriendIdByMemberId(memberIds, addFriendIds);
        // 获取对好友的设置列表
        List<String> keys = friendIds.stream().map(friendId -> CommonUtils.getFriendSettingCacheKey(userId, friendId)).collect(Collectors.toList());
        List<SysUserFriendSetting> friendSettingList = new ArrayList<>(userFriendSettingCache.getBatch(keys).values());
        // 获取当前群聊的所有用户信息
        Map<Long, SysUser> userMap = userCache.getBatch(memberIds);
        // 获取所有群成员的个人群聊设置
        List<String> personalSettingKeys = memberIds.stream().map(id -> groupRoomId + ":" + id).collect(Collectors.toList());
        Map<String, ChatGroupPersonalSetting> chatGroupPersonalSettingMap = chatGroupPersonalSettingCache.getBatch(personalSettingKeys);
        // 获取群聊的管理员列表
        List<ChatGroupManager> managerAllList = chatGroupManagerCache.getManagerAllList(roomId);
        return ChatAdapt.buildGroupDetailInfo(
                userId,
                memberIds,
                friendIds,
                chatGroupSetting,
                chatGroupPersonalSetting,
                friendSettingList,
                userMap,
                chatGroupPersonalSettingMap,
                managerAllList
        );
    }

    /**
     * 退出聊天群
     *
     * @param userId 用户 ID
     * @param roomId 房间 ID
     * @author qingmeng
     * @createTime: 2023/12/09 14:58:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void quitChatGroup(Long userId, Long roomId) {
        checkGroupRoom(roomId);
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        Long groupRoomId = chatGroupRoom.getId();
        checkNotInGroup(userId, roomId);
        chatGroupMemberDao.removeMember(userId, groupRoomId);
        chatGroupPersonalSettingCache.delete(groupRoomId + ":" + userId);
        chatGroupMemberCache.evictMemberUidList(roomId);
        // 检查是否为群管理员
        boolean flag = hasGroupManager(groupRoomId, userId);
        if (flag) {
            chatGroupManagerCache.evictManagerIdList(roomId);
            chatGroupManagerCache.evictManagerAllList(roomId);
        }
    }

    /**
     * 判断是否为管理员
     *
     * @param groupRoomId 组会议室 ID
     * @param userId      用户 ID
     * @return boolean true是管理员 false不是管理员
     * @author qingmeng
     * @createTime: 2023/12/11 11:16:12
     */
    private boolean hasGroupManager(Long groupRoomId, Long userId) {
        return Objects.nonNull(chatGroupManagerDao.getManager(groupRoomId, userId));
    }

    /**
     * 获取聊天室
     *
     * @param roomId 房间 ID
     * @author qingmeng
     * @createTime: 2023/12/10 11:12:33
     */
    private void checkChatRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomCache.get(roomId);
        AssertUtils.isNull(chatRoom, "邀请失败，群不存在");
        AssertUtils.equal(chatRoom.getRoomType(), RoomTypeEnum.GROUP.getCode(), "类型校验失败");
    }

    /**
     * 从成员id中找出我的好友id
     *
     * @param memberIds    成员id
     * @param allFriendIds 所有好友id
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/12/09 14:21:49
     */
    private List<Long> getFriendIdByMemberId(List<Long> memberIds, List<Long> allFriendIds) {
        List<Long> intersection = new ArrayList<>(memberIds);
        intersection.retainAll(allFriendIds);
        return intersection;
    }

    /**
     * 检查管理
     *
     * @param ids           IDS
     * @param managementIds 管理 ID
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/12/09 13:27:05
     */
    private List<Long> checkManagement(List<Long> ids, List<Long> managementIds) {
        Set<Long> set = new LinkedHashSet<>(ids);
        // 合并managementIds，并自动去重
        set.addAll(managementIds);
        return new ArrayList<>(set);
    }

    /**
     * 检测是否在群里
     *
     * @param userId 用户 ID
     * @param roomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:28:01
     */
    private void checkNotInGroup(Long userId, Long roomId) {
        List<Long> memberUserIdList = chatGroupMemberCache.getMemberUserIdList(roomId);
        boolean contains = memberUserIdList.contains(userId);
        AssertUtils.isTrue(contains, "非群聊成员，无法操作");
    }

    /**
     * 检测是否在群里
     *
     * @param userId 用户 ID
     * @param roomId 房间 ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:28:01
     */
    private void checkInGroup(Long userId, Long roomId) {
        List<Long> memberUserIdList = chatGroupMemberCache.getMemberUserIdList(roomId);
        boolean contains = memberUserIdList.contains(userId);
        AssertUtils.isTrue(contains, "已加入群聊，请勿重复操作");
    }

    /**
     * 检查小组房间
     *
     * @param roomId ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:26:08
     */
    private void checkGroupRoom(Long roomId) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomCache.get(roomId);
        AssertUtils.isNull(chatGroupRoom, "群聊不存在");
        AssertUtils.equal(chatGroupRoom.getRoomStatus(), RoomStatusEnum.BANNED.getCode(), "该群聊不可操作");
    }

    /**
     * 添加群组成员记录
     *
     * @param groupRoomId 组会议室 ID
     * @param ids         IDS
     * @author qingmeng
     * @createTime: 2023/12/07 08:57:35
     */
    private void addGroupMemberRecord(Long groupRoomId, List<Long> ids) {
        List<ChatGroupMember> saveChatGroupMemberList = ChatAdapt.buildSaveChatGroupMemberList(groupRoomId, ids);
        chatGroupMemberDao.saveBatch(saveChatGroupMemberList);
    }


    /**
     * 预检查用户列表
     *
     * @param userIds 用户 ID
     * @author qingmeng
     * @createTime: 2023/12/07 08:44:15
     */
    private List<Long> preCheckUserList(List<Long> userIds, Integer minNum) {
        List<Long> ids = userIds.stream().distinct().collect(Collectors.toList());
        AssertUtils.checkLessThan(ids.size(), minNum, "用户数量不能少于" + minNum);
        return ids;
    }
}
