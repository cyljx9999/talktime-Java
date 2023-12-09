package com.qingmeng.service.impl;

import com.qingmeng.config.adapt.ChatAdapt;
import com.qingmeng.config.adapt.RoomAdapt;
import com.qingmeng.config.annotation.RedissonLock;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.*;
import com.qingmeng.dto.chatGroup.*;
import com.qingmeng.entity.*;
import com.qingmeng.enums.chat.RoomStatusEnum;
import com.qingmeng.service.FileService;
import com.qingmeng.service.GroupService;
import com.qingmeng.utils.AssertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
        Long groupRoomId = inviteDTO.getGroupRoomId();
        checkGroupRoom(groupRoomId);
        List<Long> ids = preCheckUserList(inviteDTO.getUserIds(), SystemConstant.INVITE_MEMBER_MIN_COUNT);
        Long memberCount = chatGroupMemberDao.getMemberCountByGroupRoomId(groupRoomId);
        AssertUtils.equal(memberCount + ids.size(), SystemConstant.MEMBER_MAX_COUNT, "邀请人数已超过限制");
        if (memberCount > SystemConstant.INVITE_REMIND_COUNT) {
            SysUser sysUser = userCache.get(userId);
            // todo 缓存改造
            ChatGroupSetting chatGroupSetting = chatGroupSettingDao.getSettingByGroupRoomId(groupRoomId);
            //List<WsGroupInviteVO> wsGroupInviteListVO = ChatAdapt.buildInviteGroupList(userId,ids,chatGroupSetting,sysUser.getUserName());
            // 发送邀请加群通知
        } else {
            // 直接添加群成员记录
            addGroupMemberRecord(groupRoomId, ids);
        }
    }

    /**
     * 接受邀请
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 08:33:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptInvite(Long userId, Long groupRoomId) {
        checkGroupRoom(groupRoomId);
        checkInGroup(userId, groupRoomId);
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
     * @param kickOutDTO 踢出 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 09:29:11
     */
    @Override
    public void kickOut(KickOutDTO kickOutDTO) {
        checkGroupRoom(kickOutDTO.getGroupRoomId());
        checkNotInGroup(kickOutDTO.getUserId(), kickOutDTO.getGroupRoomId());
        chatGroupMemberDao.removeMember(kickOutDTO.getUserId(), kickOutDTO.getGroupRoomId());
    }

    /**
     * 更改设置
     *
     * @param alterGroupSettingDTO 更改组设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 10:32:12
     */
    @Override
    public void alterSetting(AlterGroupSettingDTO alterGroupSettingDTO) {
        checkGroupRoom(alterGroupSettingDTO.getGroupRoomId());
        chatGroupSettingDao.updateSetting(alterGroupSettingDTO);
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
        // 检查群组ID是否有效
        checkGroupRoom(addManagementDTO.getGroupRoomId());
        // 检查用户ID是否有效
        List<Long> ids = preCheckUserList(addManagementDTO.getUserIds(), SystemConstant.ADD_MANAGEMENT_MIN_COUNT);
        //  检查用户是否已经在管理员中
        List<Long> managementIds = chatGroupManagerDao.getManagementListByGroupRoomId(addManagementDTO.getGroupRoomId()).stream().map(ChatGroupManager::getUserId).collect(Collectors.toList());
        List<Long> saveIds = checkManagement(ids, managementIds);
        // 获取群管理员数量
        Long managementCount = chatGroupManagerDao.getManagementCountByByGroupRoomId(addManagementDTO.getGroupRoomId());
        // 检查群管理员数量是否超过最大值
        AssertUtils.checkGreaterThan(managementCount + saveIds.size(), SystemConstant.MANAGEMENT_MAX_COUNT, "群管理员最多可能添加" + SystemConstant.MANAGEMENT_MAX_COUNT + "个");
        // 根据群组ID和用户ID构建群管理员列表
        List<ChatGroupManager> chatGroupManagers = ChatAdapt.buildChatGroupManagementList(addManagementDTO.getGroupRoomId(), saveIds);
        // 保存群管理员列表
        chatGroupManagerDao.saveBatch(chatGroupManagers);
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
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:28:01
     */
    private void checkNotInGroup(Long userId, Long groupRoomId) {
        ChatGroupMember chatGroupMember = chatGroupMemberDao.getByUserIdAndGroupRoomId(userId, groupRoomId);
        AssertUtils.isNull(chatGroupMember, "非群聊成员，无法操作");
    }

    /**
     * 检测是否在群里
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:28:01
     */
    private void checkInGroup(Long userId, Long groupRoomId) {
        ChatGroupMember chatGroupMember = chatGroupMemberDao.getByUserIdAndGroupRoomId(userId, groupRoomId);
        AssertUtils.isNotNull(chatGroupMember, "已加入群聊，请勿重复操作");
    }

    /**
     * 检查小组房间
     *
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 09:26:08
     */
    private void checkGroupRoom(Long groupRoomId) {
        ChatGroupRoom chatGroupRoom = chatGroupRoomDao.getById(groupRoomId);
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
