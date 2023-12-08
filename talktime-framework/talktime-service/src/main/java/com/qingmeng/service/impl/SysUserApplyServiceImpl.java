package com.qingmeng.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingmeng.config.adapt.FriendAdapt;
import com.qingmeng.config.adapt.UserSettingAdapt;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.dao.SysUserApplyDao;
import com.qingmeng.dao.SysUserFriendSettingDao;
import com.qingmeng.dto.common.PageDTO;
import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.enums.user.ApplyFriendChannelEnum;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.service.ChatFriendRoomService;
import com.qingmeng.service.SysUserApplyService;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.config.strategy.applyFriend.ApplyFriendFactory;
import com.qingmeng.config.strategy.applyFriend.ApplyFriendStrategy;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.common.CommonPageVO;
import com.qingmeng.vo.user.FriendApplyRecordVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
    private SysUserFriendService sysUserFriendService;
    @Resource
    private ChatFriendRoomService chatFriendRoomService;
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
        // 根据getApplyChannel获取对应渠道的枚举进而获取对应处理的策略实现类
        ApplyFriendChannelEnum channelEnum = ApplyFriendChannelEnum.get(applyFriendDTO.getApplyChannel());
        ApplyFriendStrategy strategyWithType = applyFriendFactory.getStrategyWithType(channelEnum.getValue());
        // 调用具体实现类的applyFriend方法
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
        SysUserApply sysUserApply = checkApplyExist(agreeApplyFriendDTO.getApplyId());
        AssertUtils.equal(sysUserApply.getApplyStatus(),ApplyStatusEnum.APPLYING.getCode(), "非法申请状态");
        // 判断好友是否已存在
        checkFriendExist(sysUserApply);
        // 获取id组合标识key
        List<Long> ids = Arrays.asList(sysUserApply.getUserId(), sysUserApply.getTargetId());
        String tagKey = CommonUtils.getKeyBySort(ids);
        // 新增好友设置
        List<SysUserFriendSetting> friendSettingList = UserSettingAdapt.buildDefaultSysUserFriendSetting(ids,tagKey,sysUserApply.getApplyChannel());
        sysUserFriendSettingDao.saveOrUpdateBatchByCustom(friendSettingList);
        // 新增好友记录
        sysUserFriendService.saveFriendRecord(sysUserApply);
        // 创建聊天房间
        chatFriendRoomService.saveChatFriendRoom(ids);
        // todo 发送默认同意申请信息

        // 修改申请记录为已同意
        sysUserApplyDao.agreeApply(agreeApplyFriendDTO.getApplyId());
        // 删除好友列表缓存
        userCache.evictFriendList(sysUserApply.getUserId());
        userCache.evictFriendList(sysUserApply.getTargetId());
    }


    /**
     * 根据userId获取好友申请列表
     *
     * @param userId  用户 ID
     * @param pageDTO 分页 dto
     * @return {@link CommonPageVO }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:05:37
     */
    @Override
    public CommonPageVO<FriendApplyRecordVO> getFriendApplyListByUserId(Long userId, PageDTO pageDTO) {
        IPage<SysUserApply> page = new Page<>(pageDTO.getPageNo(), pageDTO.getSize());
        // 根据用户ID获取好友申请列表
        IPage<SysUserApply> pageList = sysUserApplyDao.getFriendApplyPageListByUserId(userId, page);
        // 通过申请记录中的申请人id获取具体用户信息
        List<SysUser> userList = getUserListByApplyList(pageList.getRecords());
        // 更新此次的申请列表中的记录为已读状态
        sysUserApplyDao.readApplyList(userId,pageList.getRecords().stream().map(SysUserApply::getId).collect(Collectors.toList()));
        // 返回好友申请记录列表VO
        return FriendAdapt.buildFriendApplyRecordPageListVO(pageList,userList);
    }

    /**
     * 根据userId获取未读申请记录计数
     *
     * @param userId 用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2023/11/29 09:09:59
     */
    @Override
    public Long getUnReadApplyRecordCountByUserId(Long userId) {
        return sysUserApplyDao.getUnReadApplyRecordCountByUserId(userId);
    }

    /**
     * 拉黑申请记录
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 10:33:45
     */
    @Override
    public void blockApplyRecord(Long applyId) {
        // 检验请求是否存在
        SysUserApply userApply = checkApplyExist(applyId);
        // 检查当前用户请求的合法性
        checkLegal(StpUtil.getLoginIdAsLong(), userApply);
        sysUserApplyDao.blockApplyRecord(applyId);
    }

    /**
     * 取消拉黑申请记录
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 11:24:22
     */
    @Override
    public void cancelBlockApplyRecord(Long applyId) {
       // 检验请求是否存在
       SysUserApply userApply = checkApplyExist(applyId);
        // 检查当前用户请求的合法性
        checkLegal(StpUtil.getLoginIdAsLong(), userApply);
        // 根据申请id取消拉黑申请
        sysUserApplyDao.cancelBlockApplyRecord(applyId);
    }

    /**
     * 获取拉黑申请记录列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 10:50:08
     */
    @Override
    public List<FriendApplyRecordVO> getBlockApplyListByUserId(Long userId) {
        List<SysUserApply> applyList = sysUserApplyDao.getBlockApplyListByUserId(userId);
        // 通过申请记录中的申请人id获取具体用户信息
        List<SysUser> userList = getUserListByApplyList(applyList);
        // 返回好友申请记录列表VO
        return FriendAdapt.buildFriendApplyRecordListVO(applyList,userList);
    }

    /**
     * 按用户 ID 删除申请记录
     *
     * @param userId    用户
     * @param applyId 申请 ID
     * @author qingmeng
     * @createTime: 2023/11/29 11:08:01
     */
    @Override
    public void deleteApplyRecordByUserId(Long userId, Long applyId) {
        SysUserApply userApply = checkApplyExist(applyId);
        checkLegal(userId, userApply);
        sysUserApplyDao.removeById(applyId);
    }

    /**
     * 检查好友是否存在
     *
     * @param sysUserApply sys 用户申请
     * @author qingmeng
     * @createTime: 2023/12/01 09:40:40
     */
    private void checkFriendExist(SysUserApply sysUserApply) {
        SysUserFriend sysUserFriend = sysUserFriendService.getFriendByBothId(sysUserApply.getUserId(), sysUserApply.getTargetId());
        AssertUtils.isNotNull(sysUserFriend,"对方已经是您好友，请勿重复操作");
    }

    /**
     * 检查合法性
     *
     * @param userId    用户 ID
     * @param userApply 用户申请
     * @author qingmeng
     * @createTime: 2023/11/29 12:41:47
     */
    private static void checkLegal(Long userId, SysUserApply userApply) {
        AssertUtils.equal(userApply.getTargetId(), userId,"非法请求");
    }

    /**
     * 检查申请记录是否存在
     *
     * @param applyId 应用 ID
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/29 11:18:57
     */
    private SysUserApply checkApplyExist(Long applyId) {
        SysUserApply userApply = sysUserApplyDao.getById(applyId);
        AssertUtils.isNull(userApply, "无效的applyId");
        return userApply;
    }

    /**
     * 通过申请列表获取用户列表
     *
     * @param applyList 申请列表
     * @return {@link List }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/29 10:56:21
     */
    @NotNull
    private List<SysUser> getUserListByApplyList(List<SysUserApply> applyList) {
        // 获取申请列表中用户ID列表
        List<Long> userIds = applyList.stream().map(SysUserApply::getUserId).collect(Collectors.toList());
        // 根据用户ID列表获取用户列表
        return new ArrayList<>(userCache.getBatch(userIds).values());
    }
}
