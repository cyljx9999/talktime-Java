package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatGroupMember;
import com.qingmeng.mapper.ChatGroupMemberMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群聊成员表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatGroupMemberDao extends ServiceImpl<ChatGroupMemberMapper, ChatGroupMember> {

}
