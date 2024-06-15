package com.qingmeng.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatEmoji;
import com.qingmeng.mapper.ChatEmojiMapper;
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
public class ChatEmojiDao extends ServiceImpl<ChatEmojiMapper, ChatEmoji> {

    /**
     * 按用户 ID 获取列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link ChatEmoji }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:13:46
     */
    public List<ChatEmoji> getListByUserId(Long userId) {
        return lambdaQuery().eq(ChatEmoji::getUserId, userId).list();
    }

    /**
     * Get By URL和用户ID
     *
     * @param expressionUrl 表达式 URL
     * @param userId        用户 ID
     * @return {@link ChatEmoji }
     * @author qingmeng
     * @createTime: 2024/06/15 13:33:13
     */
    public ChatEmoji getByURLandUserId(String expressionUrl, Long userId) {
        return lambdaQuery().eq(ChatEmoji::getExpressionUrl, expressionUrl).eq(ChatEmoji::getUserId, userId).one();
    }
}
