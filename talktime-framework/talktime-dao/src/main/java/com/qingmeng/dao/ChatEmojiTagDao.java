package com.qingmeng.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.chat.ChatEmojiTagDTO;
import com.qingmeng.entity.ChatEmojiTag;
import com.qingmeng.mapper.ChatEmojiTagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表情包 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-15 01:01:47
 */
@Service
public class ChatEmojiTagDao extends ServiceImpl<ChatEmojiTagMapper, ChatEmojiTag> {

    /**
     * 按用户 ID 获取列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link ChatEmojiTag }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:14:35
     */
    public List<ChatEmojiTag> getListByUserId(Long userId) {
        return lambdaQuery().eq(ChatEmojiTag::getUserId, userId).list();
    }

    /**
     * 按名称和用户 ID 获取
     *
     * @param chatEmojiTagDTO 聊天表情符号标签 DTO
     * @param userId          用户 ID
     * @return {@link ChatEmojiTag }
     * @author qingmeng
     * @createTime: 2024/06/15 17:16:54
     */
    public ChatEmojiTag getByNameAndUserId(ChatEmojiTagDTO chatEmojiTagDTO, Long userId) {
        return lambdaQuery().eq(ChatEmojiTag::getTagName, chatEmojiTagDTO.getTagName()).eq(ChatEmojiTag::getUserId, userId).one();
    }
}
