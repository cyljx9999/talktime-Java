package com.qingmeng.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.dao.SysUserFriendSettingDao;
import com.qingmeng.entity.SysUserFriendSetting;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户好友设置缓存
 * @createTime 2023年11月27日 14:40:00
 */
@Component
public class UserFriendSettingCache extends AbstractRedisStringCache<String, SysUserFriendSetting> {
    @Resource
    private SysUserFriendSettingDao sysUserFriendSettingDao;


    /**
     * 根据输入对象获取缓存的键。
     *
     * @param key 输入对象
     * @return 缓存键
     */
    @Override
    protected String getKey(String key) {
        return RedisConstant.USER_FRIEND_SETTING_KEY + key;
    }

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return 过期时间（秒）
     */
    @Override
    protected Long getExpireSeconds() {
        return RedisConstant.USER_FRIEND_SETTING_EXPIRE * 24 * 60 * 60;
    }

    /**
     * 批量加载缓存数据。
     *
     * @param keys 批量请求列表
     * @return 加载的缓存数据映射
     */
    @Override
    protected Map<String, SysUserFriendSetting> load(List<String> keys) {
        List<SysUserFriendSetting> list = sysUserFriendSettingDao.listByIds(keys);
        return list.stream().collect(Collectors.toMap(SysUserFriendSetting::getTagKey, Function.identity()));
    }
}
