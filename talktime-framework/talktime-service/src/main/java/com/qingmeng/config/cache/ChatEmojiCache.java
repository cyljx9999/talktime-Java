package com.qingmeng.config.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.dao.ChatEmojiDao;
import com.qingmeng.dao.ChatEmojiTagDao;
import com.qingmeng.entity.ChatEmoji;
import com.qingmeng.entity.ChatEmojiTag;
import com.qingmeng.utils.JsonUtils;
import com.qingmeng.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月15日 13:21:00
 */
@Component
public class ChatEmojiCache {
    @Resource
    private ChatEmojiDao chatEmojiDao;
    @Resource
    private ChatEmojiTagDao chatEmojiTagDao;

    /**
     * 按用户 ID 获取列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link ChatEmoji }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:22:25
     */
    public List<ChatEmoji> getListByUserId(Long userId) {
        String key = RedisConstant.CHAT_EMOJI_KEY + userId;
        if (!RedisUtils.hasKey(key)) {
            List<ChatEmoji> chatEmojiList = chatEmojiDao.getListByUserId(userId);
            List<String> collect = chatEmojiList.stream().map(JsonUtils::toStr).collect(Collectors.toList());
            RedisUtils.lRightPushAll(key, collect);
        }
        return RedisUtils.lRange(key, 0, -1, ChatEmoji.class);
    }

    /**
     * 按用户 ID 获取标签列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link ChatEmojiTag }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:22:25
     */
    public List<ChatEmojiTag> getTagListByUserId(Long userId) {
        String key = RedisConstant.CHAT_EMOJI_TAG_KEY + userId;
        if (!RedisUtils.hasKey(key)) {
            List<ChatEmojiTag> chatEmojiList = chatEmojiTagDao.getListByUserId(userId);
            List<String> collect = chatEmojiList.stream().map(JsonUtils::toStr).collect(Collectors.toList());
            RedisUtils.lRightPushAll(key, collect);
        }
        return RedisUtils.lRange(key, 0, -1, ChatEmojiTag.class);
    }

    /**
     * 清除全部缓存
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:38:57
     */
    public void clearAllCache(Long userId) {
        clearEmojiCache(userId);
        clearEmojiTagCache(userId);
    }

    /**
     * 清除表情缓存
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:38:57
     */
    public void clearEmojiCache(Long userId) {
        String key = RedisConstant.CHAT_EMOJI_KEY + userId;
        RedisUtils.delete(key);
    }

    /**
     * 清除标签缓存
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:38:57
     */
    public void clearEmojiTagCache(Long userId) {
        String key = RedisConstant.CHAT_EMOJI_TAG_KEY + userId;
        RedisUtils.delete(key);
    }


}
