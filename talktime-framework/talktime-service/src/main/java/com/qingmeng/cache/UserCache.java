package com.qingmeng.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.entity.SysUser;
import com.qingmeng.service.SysUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private SysUserService sysUserService;

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
        List<SysUser> list = sysUserService.listByIds(userIds);
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
    public SysUser getUserInfoById(Long userId){
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
    public List<SysUser> userListByIds(List<Long> userIds){
        Map<Long, SysUser> userMap = getBatch(userIds);
        return new ArrayList<>(userMap.values());
    }

    /**
     * 删除用户
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2023/11/22 09:35:40
     */
    public void deleteUserById(Long userId){
        delete(userId);
    }

    /**
     * 批量删除 用户
     *
     * @param userIds 用户 ID
     * @author qingmeng
     * @createTime: 2023/11/22 09:35:42
     */
    public void deleteBatchUserByIds(List<Long> userIds){
        deleteBatch(userIds);
    }

    public void offline(Long id, Date lastOperateTime) {

    }
}
