package com.qingmeng.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.entity.SysUser;
import com.qingmeng.utils.RedisUtils;
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
        return RedisConstant.USER_INFO_EXPIRE * 60L;
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
     * 获取用户信息
     *
     * @param userId 用户 ID
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 09:34:10
     */
    public SysUser getUserInfoById(Long userId) {
        return get(userId);
    }

    /**
     * 按 ID 查出用户列表
     *
     * @param userIds 用户 ID
     * @return {@link List }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/22 09:43:57
     */
    public List<SysUser> userListByIds(List<Long> userIds) {
        Map<Long, SysUser> userMap = getBatch(userIds);
        return new ArrayList<>(userMap.values());
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
}
