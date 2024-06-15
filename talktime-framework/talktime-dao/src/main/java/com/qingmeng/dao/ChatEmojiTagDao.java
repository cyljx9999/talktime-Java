package com.qingmeng.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
