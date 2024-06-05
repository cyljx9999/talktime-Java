package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 信息
 * @createTime 2024年06月04日 22:05:00
 */
@Service
public class ChatMessageDao extends ServiceImpl<ChatMessageMapper, ChatMessage> {
}
