package com.qingmeng.cache;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.dao.SysUserFriendDao;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.utils.RedisUtils;
import com.qingmeng.vo.user.FriendTypeVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户缓存类
 * @createTime 2023年11月22日 08:27:00
 */
@Component
public class UserCache extends AbstractRedisStringCache<Long, SysUser> {
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserFriendDao sysUserFriendDao;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;

    /**
     * 根据输入对象获取缓存的键。
     *
     * @param userId 输入对象
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/22 09:28:29
     */
    @Override
    protected String getKey(Long userId) {
        return RedisConstant.USER_INFO_KEY + userId;
    }

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2023/11/22 09:28:26
     */
    @Override
    protected Long getExpireSeconds() {
        return RedisConstant.USER_INFO_EXPIRE * SystemConstant.MINUTE;
    }

    /**
     * 批量加载缓存数据。
     *
     * @param userIds id集合
     * @return {@link Map }<{@link Long }, {@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/22 09:28:11
     */
    @Override
    protected Map<Long, SysUser> load(List<Long> userIds) {
        List<SysUser> list = sysUserDao.listByIds(userIds);
        return list.stream().collect(Collectors.toMap(SysUser::getId, Function.identity()));
    }

    /**
     * 离线
     *
     * @param userId          用户 ID
     * @param lastOperateTime 上次操作时间
     * @author qingmeng
     * @createTime: 2023/11/23 15:59:15
     */
    public void offline(Long userId, Date lastOperateTime) {
        //移除上线线表
        RedisUtils.zRemove(RedisConstant.ONLINE_USERID_KEY, userId);
        //更新上线表
        RedisUtils.zAdd(RedisConstant.OFFLINE_USERID_KEY, userId.toString(), lastOperateTime.getTime());
    }

    /**
     * 在线
     *
     * @param userId          用户 ID
     * @param lastOperateTime 上次操作时间
     * @author qingmeng
     * @createTime: 2023/11/23 15:59:12
     */
    public void online(Long userId, Date lastOperateTime) {
        //移除离线表
        RedisUtils.zRemove(RedisConstant.OFFLINE_USERID_KEY, userId);
        //更新上线表
        RedisUtils.zAdd(RedisConstant.ONLINE_USERID_KEY, userId.toString(), lastOperateTime.getTime());
    }

    /**
     * 是否在线
     *
     * @param userId 用户id
     * @return boolean
     * @author qingmeng
     * @createTime: 2023/11/23 16:02:04
     */
    public boolean isOnline(Long userId) {
        return Objects.nonNull(RedisUtils.zScore(RedisConstant.ONLINE_USERID_KEY, userId));
    }

    /**
     * 获取好友列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendTypeVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 12:37:20
     */
    @Cacheable(value = "friendList", key = "#userId")
    public List<FriendTypeVO> getFriendList(Long userId) {
        // 获取当前用户的好友id列表
        List<Long> friendIds = sysUserFriendDao.getFriendListById(userId)
                .stream().distinct()
                .map(SysUserFriend::getFriendId)
                .collect(Collectors.toList());
        // 获取完整的好友数据列表
        List<SysUser> sysUsers = new ArrayList<>(getBatch(friendIds).values());

        // 根据用户名首字符按照字母分类表进行归类处理
        Map<String, List<SysUser>> listMap = sysUsers.stream().collect(Collectors.groupingBy(user -> {
            char ch = user.getUserName().charAt(0);
            String firstLetter = CommonUtils.getFirstLetter(ch);
            return SystemConstant.alphabetList.contains(firstLetter) ? firstLetter : "#";
        }));
        // 获取当前用户对好友的设置列表
        List<String> keys = friendIds.stream().map(friendId -> CommonUtils.getFriendSettingCacheKey(userId, friendId)).collect(Collectors.toList());
        List<SysUserFriendSetting> friendSettings = new ArrayList<>(userFriendSettingCache.getBatch(keys).values());
        // 构造最后的信息返回类
        return FriendAdapt.buildFriendList(listMap,friendSettings,userId);
    }

    /**
     * 删除好友列表缓存
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendTypeVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 12:40:43
     */
    @CacheEvict(cacheNames = "friendList", key = "#userId")
    public List<FriendTypeVO> evictFriendList(Long userId) {
        return null;
    }

}
