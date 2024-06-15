package com.qingmeng.service.impl;

import com.qingmeng.config.adapt.ChatAdapt;
import com.qingmeng.config.cache.ChatEmojiCache;
import com.qingmeng.dao.ChatEmojiTagDao;
import com.qingmeng.dto.chat.ChatEmojiTagDTO;
import com.qingmeng.entity.ChatEmojiTag;
import com.qingmeng.service.ChatEmojiTagService;
import com.qingmeng.utils.AssertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月15日 13:06:00
 */
@Service
public class ChatEmojiTagServiceImpl implements ChatEmojiTagService {
    @Resource
    private ChatEmojiTagDao chatEmojiTagDao;
    @Resource
    private ChatEmojiCache chatEmojiCache;

    /**
     * 插入
     *
     * @param chatEmojiTagDTO 聊天表情符号标签 DTO
     * @param userId          用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 17:14:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(ChatEmojiTagDTO chatEmojiTagDTO, Long userId) {
        ChatEmojiTag chatEmojiTag = chatEmojiTagDao.getByNameAndUserId(chatEmojiTagDTO,userId);
        AssertUtils.isNotNull(chatEmojiTag,"表情包标签已存在");
        ChatEmojiTag chatEmojiTagInsert = ChatAdapt.buildChatEmojiTag(chatEmojiTagDTO,userId);
        chatEmojiTagDao.save(chatEmojiTagInsert);
        chatEmojiCache.clearEmojiTagCache(userId);
    }

    /**
     * 删除
     *
     * @param tagId  标记 ID
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 17:20:57
     */
    @Override
    public void remove(Long tagId, Long userId) {
        ChatEmojiTag byId = chatEmojiTagDao.getById(tagId);
        AssertUtils.isNull(byId,"表情包标签不存在");
        AssertUtils.equal(byId.getUserId(),userId,"不能删除别人创建的表情包标签");
        chatEmojiTagDao.removeById(tagId);
    }
}
