package com.qingmeng.service.impl;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.adapt.UserSettingAdapt;
import com.qingmeng.cache.UserCache;
import com.qingmeng.cache.UserFriendSettingCache;
import com.qingmeng.dao.SysUserFriendDao;
import com.qingmeng.dao.SysUserFriendSettingDao;
import com.qingmeng.dto.login.CheckFriendDTO;
import com.qingmeng.dto.login.CheckFriendListDTO;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.user.CheckFriendVO;
import com.qingmeng.vo.user.FriendTypeVO;
import com.qingmeng.vo.user.UserFriendSettingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:19:00
 */
@Service
public class SysUserFriendServiceImpl implements SysUserFriendService {
    @Resource
    private SysUserFriendSettingDao sysUserFriendSettingDao;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;
    @Resource
    private SysUserFriendDao sysUserFriendDao;
    @Resource
    private UserCache userCache;


    /**
     * 通过两个 ID 获取设置
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link UserFriendSettingVO }
     * @author qingmeng
     * @createTime: 2023/11/29 14:41:32
     */
    @Override
    public UserFriendSettingVO getFriendSettingByBothId(Long userId, Long friendId) {
        String cacheKey = CommonUtils.getFriendSettingCacheKey(userId, friendId);
        SysUserFriendSetting friendSetting = checkLegal(cacheKey);
        return UserSettingAdapt.buildUserFriendSettingVO(friendSetting);
    }


    /**
     * 更改设置
     *
     * @param userId               用户 ID
     * @param userFriendSettingDTO 用户好友设置 DTO
     * @author qingmeng
     * @createTime: 2023/11/29 15:15:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterFriendSetting(Long userId, UserFriendSettingDTO userFriendSettingDTO) {
        String cacheKey = CommonUtils.getFriendSettingCacheKey(userId, userFriendSettingDTO.getFriendId());
        SysUserFriendSetting friendSetting = checkLegal(cacheKey);
        AssertUtils.equal(friendSetting.getUserId(),userId, "非法请求");
        sysUserFriendSettingDao.alterSetting(userFriendSettingDTO);
        userFriendSettingCache.delete(cacheKey);
        userCache.evictFriendList(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFriendRecord(SysUserApply sysUserApply) {
        SysUserFriend saveUserFriend = FriendAdapt.buildFriendRecord(sysUserApply);
        SysUserFriend saveUserFriendReverse = FriendAdapt.buildFriendRecordReverse(sysUserApply);
        sysUserFriendDao.saveOrUpdateByCustom(saveUserFriend);
        sysUserFriendDao.saveOrUpdateByCustom(saveUserFriendReverse);
    }

    /**
     * 通过两个ID获取好友
     *
     * @param userId   用户 ID
     * @param targetId 目标 ID
     * @return {@link SysUserFriend }
     * @author qingmeng
     * @createTime: 2023/12/01 16:52:47
     */
    @Override
    public SysUserFriend getFriendByBothId(Long userId, Long targetId) {
        return sysUserFriendDao.getFriendByBothId(userId, targetId);
    }

    /**
     * 获取好友列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendTypeVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 10:43:34
     */
    @Override
    public List<FriendTypeVO> getFriendList(Long userId) {
        return userCache.getFriendList(userId);
    }

    /**
     * 检查是否为好友
     *
     * @param userId         用户 ID
     * @param checkFriendDTO 检查好友 DTO
     * @return {@link CheckFriendVO }
     * @author qingmeng
     * @createTime: 2023/12/03 13:08:56
     */
    @Override
    public CheckFriendVO checkFriend(Long userId, CheckFriendDTO checkFriendDTO) {
        SysUserFriend sysUserFriend = sysUserFriendDao.getFriendById(userId);
        return FriendAdapt.buildCheckFriend(sysUserFriend, checkFriendDTO);
    }

    /**
     * 批量检查是否为好友
     *
     * @param userId         用户 ID
     * @param checkFriendListDTO 检查好友 DTO
     * @return {@link List }<{@link CheckFriendVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 12:49:25
     */
    @Override
    public List<CheckFriendVO> checkFriendList(Long userId, CheckFriendListDTO checkFriendListDTO) {
        List<Long> friendIds = sysUserFriendDao.getFriendListById(userId)
                .stream().distinct()
                .map(SysUserFriend::getFriendId)
                .collect(Collectors.toList());
        return FriendAdapt.buildCheckFriendList(friendIds, checkFriendListDTO);
    }

    /**
     * 检查合法性
     *
     * @param cacheKey 缓存键
     * @return {@link SysUserFriendSetting }
     * @author qingmeng
     * @createTime: 2023/11/29 15:31:44
     */
    private SysUserFriendSetting checkLegal(String cacheKey) {
        SysUserFriendSetting friendSetting = userFriendSettingCache.get(cacheKey);
        AssertUtils.isNull(friendSetting, "未找到好友设置");
        return friendSetting;
    }
}
