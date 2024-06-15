package com.qingmeng.service;


import com.qingmeng.dto.chat.ChatEmojiTagDTO;

/**
 * <p>
 * 用户表情包 服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-15 01:01:47
 */
public interface ChatEmojiTagService {

    /**
     * 插入
     *
     * @param chatEmojiTagDTO 聊天表情符号标签 DTO
     * @param userId          用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 17:14:12
     */
    void insert(ChatEmojiTagDTO chatEmojiTagDTO, Long userId);

    /**
     * 删除
     *
     * @param tagId  标记 ID
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 17:20:57
     */
    void remove(Long tagId, Long userId);
}
