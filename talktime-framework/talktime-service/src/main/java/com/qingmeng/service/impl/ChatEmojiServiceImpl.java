package com.qingmeng.service.impl;

import com.qingmeng.config.adapt.ChatAdapt;
import com.qingmeng.config.annotation.RedissonLock;
import com.qingmeng.config.cache.ChatEmojiCache;
import com.qingmeng.dao.ChatEmojiDao;
import com.qingmeng.dto.chat.ChatEmojiDTO;
import com.qingmeng.entity.ChatEmoji;
import com.qingmeng.entity.ChatEmojiTag;
import com.qingmeng.service.ChatEmojiService;
import com.qingmeng.utils.AssertUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月15日 13:06:00
 */
@Service
public class ChatEmojiServiceImpl implements ChatEmojiService {
    @Resource
    private ChatEmojiCache chatEmojiCache;
    @Resource
    @Lazy
    private ChatEmojiDao chatEmojiDao;

    /**
     * 获取表情包列表
     *
     * @param userId 用户 ID
     * @return {@link Map }<{@link String }, {@link Object }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:12:08
     */
    @Override
    public Map<String, Object> getEmojisList(Long userId) {
        Map<String, Object> map = new HashMap<>(2);
        List<ChatEmoji> chatEmojiList = chatEmojiCache.getListByUserId(userId);
        List<ChatEmojiTag> chatEmojiTagList = chatEmojiCache.getTagListByUserId(userId);
        map.put("emojiList", chatEmojiList);
        map.put("tagList", chatEmojiTagList);
        return map;
    }

    /**
     * 插入
     *
     * @param chatEmojiDTO 聊天表情符号 DTO
     * @param userId       用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:29:42
     */
    @Override
    @RedissonLock(key = "#userId")
    public void insert(ChatEmojiDTO chatEmojiDTO, Long userId) {
        ChatEmoji chatEmoji = chatEmojiDao.getByURLandUserId(chatEmojiDTO.getExpressionUrl(), userId);
        AssertUtils.isNotNull(chatEmoji, "表情包已存在");
        ChatEmoji chatEmojiInsert = ChatAdapt.buildInsertChatEmoji(chatEmojiDTO, userId);
        chatEmojiDao.save(chatEmojiInsert);
        chatEmojiCache.clearEmojiCache(userId);
    }


    /**
     * 删除
     *
     * @param emojiId 表情符号 ID
     * @param userId  用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:35:36
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long emojiId, Long userId) {
        ChatEmoji chatEmoji = chatEmojiDao.getById(userId);
        AssertUtils.isNull(chatEmoji, "表情包Id不存在");
        AssertUtils.equal(chatEmoji.getUserId(), userId, "非本人表情包不能删除");
        chatEmojiDao.removeById(emojiId);
        chatEmojiCache.clearAllCache(userId);
    }
}
