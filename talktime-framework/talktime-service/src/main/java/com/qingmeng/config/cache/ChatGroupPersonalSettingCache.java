package com.qingmeng.config.cache;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.ChatGroupPersonalSettingDao;
import com.qingmeng.entity.ChatGroupPersonalSetting;
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
 * @Description 抽象房间缓存
 * @createTime 2023年12月10日 09:22:00
 */
@Component
public class ChatGroupPersonalSettingCache extends AbstractRedisStringCache<String, ChatGroupPersonalSetting> {
    @Resource
    private ChatGroupPersonalSettingDao chatGroupPersonalSettingDao;

    /**
     * 根据输入对象获取缓存的键。
     *
     * @param key 输入对象 (groupRoomId:userId)
     * @return 缓存键
     */
    @Override
    protected String getKey(String key) {
        return RedisConstant.CHAT_PERSONAL_SETTING_KEY + key;
    }

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return 过期时间（秒）
     */
    @Override
    protected Long getExpireSeconds() {
        return RedisConstant.CHAT_PERSONAL_SETTING_EXPIRE * SystemConstant.DAY;
    }

    /**
     * 批量加载缓存数据。
     *
     * @param keys 批量请求列表 (groupRoomId:userId)
     * @return 加载的缓存数据映射
     */
    @Override
    protected Map<String, ChatGroupPersonalSetting> load(List<String> keys) {
        ArrayList<ChatGroupPersonalSetting> list = new ArrayList<>();
        keys.forEach(key ->{
            String[] split = key.split(StrUtil.COLON);
            ChatGroupPersonalSetting setting = chatGroupPersonalSettingDao.getByGroupRoomIdAndUserId(split[0],split[1]);
            list.add(setting);
        });
        return list.stream().collect(Collectors.toMap(item -> item.getGroupRoomId() + StrUtil.COLON + item.getUserId(), Function.identity()));
    }


}
