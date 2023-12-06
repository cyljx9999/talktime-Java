package com.qingmeng.service.impl;

import com.qingmeng.adapt.ChatAdapt;
import com.qingmeng.adapt.RoomAdapt;
import com.qingmeng.cache.UserCache;
import com.qingmeng.dao.*;
import com.qingmeng.dto.group.CreatGroupDTO;
import com.qingmeng.entity.*;
import com.qingmeng.service.FileService;
import com.qingmeng.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    private ChatGroupManagerDao chatGroupManagersDao;
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
     * @param creatGroupDTO 创建集团 DTO
     * @author qingmeng
     * @createTime: 2023/12/06 22:07:59
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void creatGroup(Long userId, CreatGroupDTO creatGroupDTO) {
        // 创建抽象群聊房间记录
        ChatRoom chatRoom = RoomAdapt.buildDefaultGroupRoom();
        chatRoomDao.save(chatRoom);
        Long roomId = chatRoom.getId();
        // 创建群聊房间记录
        ChatGroupRoom chatGroupRoom = ChatAdapt.buildChatGroupRoom(roomId);
        Long groupRoomId = chatGroupRoom.getId();
        // 添加群成员记录
        List<ChatGroupMember> saveChatGroupMemberList = ChatAdapt.buildSaveChatGroupMemberList(groupRoomId, creatGroupDTO.getMemberIds());
        chatGroupMemberDao.saveBatch(saveChatGroupMemberList);
        // 添加群管理员(即群主)记录
        ChatGroupManager chatGroupManager = ChatAdapt.buildChatGroupManager(userId, groupRoomId);
        chatGroupManagersDao.save(chatGroupManager);
        // 添加对应成员对群聊设置记录
        List<ChatGroupPersonalSetting> builtChatGroupPersonalSettingSaveList = ChatAdapt.buildChatGroupPersonalSettingSaveList(groupRoomId, creatGroupDTO.getMemberIds());
        chatGroupPersonalSettingDao.saveBatch(builtChatGroupPersonalSettingSaveList);
        // 添加管理员管理群聊设置记录
        SysUser sysUser = userCache.get(userId);
        String qrcodeUrl = fileService.getQrcodeUrl(groupRoomId);
        ChatGroupSetting chatGroupPersonalSetting = ChatAdapt.buildDefaultChatGroupSetting(groupRoomId, sysUser,qrcodeUrl);
        chatGroupSettingDao.save(chatGroupPersonalSetting);
    }
}
