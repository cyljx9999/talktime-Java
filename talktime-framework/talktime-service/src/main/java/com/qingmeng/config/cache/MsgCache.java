package com.qingmeng.config.cache;

import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息缓存
 * @createTime 2024年06月06日 11:01:00
 */
@Component
public class MsgCache {
    @Autowired
    private ChatMessageDao chatMessageDao;

    @Cacheable(cacheNames = "msg", key = "'msg'+#msgId")
    public ChatMessage getMsg(Long msgId) {
        return chatMessageDao.getById(msgId);
    }

    @CacheEvict(cacheNames = "msg", key = "'msg'+#msgId")
    public ChatMessage evictMsg(Long msgId) {
        return null;
    }
}
