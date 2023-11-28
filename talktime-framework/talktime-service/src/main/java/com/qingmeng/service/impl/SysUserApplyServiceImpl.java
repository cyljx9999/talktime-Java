package com.qingmeng.service.impl;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.adapt.RoomAdapt;
import com.qingmeng.adapt.UserSettingAdapt;
import com.qingmeng.cache.UserCache;
import com.qingmeng.dao.*;
import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.*;
import com.qingmeng.enums.user.ApplyFriendChannelEnum;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.service.SysUserApplyService;
import com.qingmeng.strategy.applyFriend.ApplyFriendFactory;
import com.qingmeng.strategy.applyFriend.ApplyFriendStrategy;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.vo.user.FriendApplyRecordVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:18:00
 */
@Service
public class SysUserApplyServiceImpl implements SysUserApplyService {
    @Resource
    private ApplyFriendFactory applyFriendFactory;
    @Resource
    private SysUserApplyDao sysUserApplyDao;
    @Resource
    private SysUserFriendSettingDao sysUserFriendSettingDao;
    @Resource
    private SysUserFriendDao sysUserFriendDao;
    @Resource
    private ChatRoomDao chatRoomDao;
    @Resource
    private ChatFriendRoomDao chatFriendRoomDao;
    @Resource
    private UserCache userCache;

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 15:56:45
     */
    @Override
    public void applyFriend(ApplyFriendDTO applyFriendDTO) {
        ApplyFriendChannelEnum channelEnum = ApplyFriendChannelEnum.get(applyFriendDTO.getApplyChannel());
        ApplyFriendStrategy strategyWithType = applyFriendFactory.getStrategyWithType(channelEnum.getValue());
        strategyWithType.applyFriend(applyFriendDTO);
    }

    /**
     * 同意申请好友
     *
     * @param agreeApplyFriendDTO 同意申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/28 17:30:48
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeApply(AgreeApplyFriendDTO agreeApplyFriendDTO) {
        SysUserApply sysUserApply = sysUserApplyDao.getById(agreeApplyFriendDTO.getId());
        AsserUtils.isNull(sysUserApply, "该申请记录不存在");
        AsserUtils.equal(sysUserApply.getApplyStatus(),ApplyStatusEnum.APPLYING.getCode(), "非法申请状态");
        // 新增好友设置
        SysUserFriendSetting saveFriendSetting = UserSettingAdapt.buildDefalutSysUserFriendSetting(sysUserApply);
        sysUserFriendSettingDao.save(saveFriendSetting);
        // 新增好友记录
        SysUserFriend saveUserFriend = FriendAdapt.buildFriendRecord(sysUserApply);
        sysUserFriendDao.save(saveUserFriend);
        // 新增抽象好友房间记录
        ChatRoom chatRoom = RoomAdapt.buildDefaultFriendRoom();
        chatRoomDao.save(chatRoom);
        // 新增好友房间记录
        ChatFriendRoom chatFriendRoom = RoomAdapt.buildChatFriendRoom(
                chatRoom.getId(),
                sysUserApply.getUserId(),
                sysUserApply.getTargetId(),
                saveFriendSetting.getTagKey()
        );
        chatFriendRoomDao.save(chatFriendRoom);
        // todo 发送默认同意申请信息

        // 修改申请记录为已同意
        sysUserApplyDao.agreeApply(agreeApplyFriendDTO.getId());
    }

    /**
     * 根据userId获取好友申请列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/28 23:22:06
     */
    @Override
    public List<FriendApplyRecordVO> getFriendApplyListByUserId(Long userId) {
        List<SysUserApply> applyList = sysUserApplyDao.getFriendApplyListByUserId(userId);
        List<Long> userIds = applyList.stream().map(SysUserApply::getUserId).collect(Collectors.toList());
        List<SysUser> userList = new ArrayList<>(userCache.getBatch(userIds).values());
        return FriendAdapt.buildFriendApplyRecordListVO(applyList,userList);
    }
}
