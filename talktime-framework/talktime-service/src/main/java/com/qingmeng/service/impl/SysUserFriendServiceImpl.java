package com.qingmeng.service.impl;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.adapt.UserSettingAdapt;
import com.qingmeng.cache.UserFriendSettingCache;
import com.qingmeng.dao.SysUserFriendDao;
import com.qingmeng.dao.SysUserFriendSettingDao;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.user.UserFriendSettingVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

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
        String cacheKey = getCacheKey(userId, friendId);
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
    public void alterFriendSetting(Long userId, UserFriendSettingDTO userFriendSettingDTO) {
        String cacheKey = getCacheKey(userId, userFriendSettingDTO.getFriendId());
        SysUserFriendSetting friendSetting = checkLegal(cacheKey);
        AsserUtils.equal(friendSetting.getUserId(),userId, "非法请求");
        sysUserFriendSettingDao.alterSetting(userFriendSettingDTO);
        userFriendSettingCache.delete(cacheKey);
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
     * 检查合法性
     *
     * @param cacheKey 缓存键
     * @return {@link SysUserFriendSetting }
     * @author qingmeng
     * @createTime: 2023/11/29 15:31:44
     */
    private SysUserFriendSetting checkLegal(String cacheKey) {
        SysUserFriendSetting friendSetting = userFriendSettingCache.get(cacheKey);
        AsserUtils.isNull(friendSetting, "未找到好友设置");
        return friendSetting;
    }

    /**
     * 获取缓存密钥
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/29 15:31:39
     */
    @NotNull
    private static String getCacheKey(Long userId, Long friendId) {
        return CommonUtils.getKeyBySort(Arrays.asList(userId, friendId)) + ":" + userId;
    }
}
