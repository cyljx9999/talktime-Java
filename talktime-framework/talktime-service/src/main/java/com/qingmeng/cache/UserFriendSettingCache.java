package com.qingmeng.cache;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserFriendSettingDao;
import com.qingmeng.entity.SysUserFriendSetting;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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
     *  (这个的key的形式是) 1-2:1 1-2为双方好友id根据排序组成的tagKey 1表示本人id
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
        return RedisConstant.USER_FRIEND_SETTING_EXPIRE * SystemConstant.DAY;
    }

    /**
     * 批量加载缓存数据。
     *  (这个的key的形式是) 1-2:1 1-2为双方好友id根据排序组成的tagKey 1表示本人id
     * @param keys 批量请求列表
     * @return 加载的缓存数据映射
     */
    @Override
    protected Map<String, SysUserFriendSetting> load(List<String> keys) {
        List<SysUserFriendSetting> list = new ArrayList<>();
        keys.forEach(key -> {
            String[] split = key.split(StrUtil.COLON);
            SysUserFriendSetting userFriendSetting = sysUserFriendSettingDao.lambdaQuery()
                    .eq(SysUserFriendSetting::getTagKey, split[0])
                    .eq(SysUserFriendSetting::getUserId, split[1])
                    .one();
            list.add(userFriendSetting);
        });
        return list.stream().collect(Collectors.toMap(item -> item.getTagKey() + StrUtil.COLON + item.getUserId(), Function.identity()));
    }
}
