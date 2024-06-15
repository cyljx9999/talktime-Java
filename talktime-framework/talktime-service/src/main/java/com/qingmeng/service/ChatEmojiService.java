package com.qingmeng.service;

import com.qingmeng.dto.chat.ChatEmojiDTO;

import java.util.Map;

/**
 * <p>
 * 用户表情包 服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-06-15 01:01:47
 */
public interface ChatEmojiService{

    /**
     * 获取表情包列表
     *
     * @param userId 用户 ID
     * @return {@link Map }<{@link String }, {@link Object }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:12:08
     */
    Map<String, Object> getEmojisList(Long userId);

    /**
     * 插入
     *
     * @param chatEmojiDTO 聊天表情符号 DTO
     * @param userId       用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:29:42
     */
    void insert(ChatEmojiDTO chatEmojiDTO, Long userId);

    /**
     * 删除
     *
     * @param emojiId 表情符号 ID
     * @param userId  用户 ID
     * @author qingmeng
     * @createTime: 2024/06/15 13:35:36
     */
    void remove(Long emojiId, Long userId);
}
