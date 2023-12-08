package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatGroupManager;
import com.qingmeng.mapper.ChatGroupManagerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 群管理员表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-26 08:12:03
 */
@Service
public class ChatGroupManagerDao extends ServiceImpl<ChatGroupManagerMapper, ChatGroupManager> {

    /**
     * 获取角色列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/08 09:02:47
     */
    public List<Integer> getRoleTypeList(Long userId) {
        return lambdaQuery()
                .eq(ChatGroupManager::getUserId, userId)
                .list()
                .stream().map(ChatGroupManager::getRoleType)
                .collect(Collectors.toList());
    }
}
