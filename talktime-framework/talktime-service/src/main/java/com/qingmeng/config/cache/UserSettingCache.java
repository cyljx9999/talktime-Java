package com.qingmeng.config.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserPrivacySettingDao;
import com.qingmeng.entity.SysUserPrivacySetting;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户设置缓存
 * @createTime 2023年11月27日 14:40:00
 */
@Component
public class UserSettingCache extends AbstractRedisStringCache<Long, SysUserPrivacySetting> {
    @Resource
    private SysUserPrivacySettingDao sysUserPrivacySettingDao;


    /**
     * 根据输入对象获取缓存的键。
     *
     * @param userId 输入对象
     * @return 缓存键
     */
    @Override
    protected String getKey(Long userId) {
        return RedisConstant.USER_PRIVACY_SETTING_KEY + userId;
    }

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return 过期时间（秒）
     */
    @Override
    protected Long getExpireSeconds() {
        return RedisConstant.USER_PRIVACY_SETTING_EXPIRE * SystemConstant.DAY;
    }

    /**
     * 批量加载缓存数据。
     *
     * @param userIds 批量请求列表
     * @return 加载的缓存数据映射
     */
    @Override
    protected Map<Long, SysUserPrivacySetting> load(List<Long> userIds) {
        List<SysUserPrivacySetting> list = sysUserPrivacySettingDao.getListByUserIds(userIds);
        return list.stream().collect(Collectors.toMap(SysUserPrivacySetting::getUserId, Function.identity()));
    }
}
