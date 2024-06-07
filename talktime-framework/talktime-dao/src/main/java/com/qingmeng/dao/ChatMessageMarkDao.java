package com.qingmeng.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.ChatMessageMark;
import com.qingmeng.enums.common.NormalOrNoEnum;
import com.qingmeng.mapper.ChatMessageMarkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天消息 Mark Dao
 *
 * @author qingmeng
 * @date 2024/06/07 23:04:41
 */
@Service
public class ChatMessageMarkDao extends ServiceImpl<ChatMessageMarkMapper, ChatMessageMark> {

    /**
     * 获取
     *
     * @param userId   用户 ID
     * @param msgId    消息 ID
     * @param markType 标记类型
     * @return {@link ChatMessageMark }
     * @author qingmeng
     * @createTime: 2024/06/07 23:06:06
     */
    public ChatMessageMark get(Long userId, Long msgId, Integer markType) {
        return lambdaQuery().eq(ChatMessageMark::getUserId, userId)
                .eq(ChatMessageMark::getMsgId, msgId)
                .eq(ChatMessageMark::getType, markType)
                .one();
    }

    /**
     * 获取标记计数
     *
     * @param msgId    消息 ID
     * @param markType 标记类型
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/07 23:06:16
     */
    public Long getMarkCount(Long msgId, Integer markType) {
        return lambdaQuery().eq(ChatMessageMark::getMsgId, msgId)
                .eq(ChatMessageMark::getType, markType)
                .eq(ChatMessageMark::getStatus, NormalOrNoEnum.NORMAL.getCode())
                .count();
    }

    /**
     * 通过 消息id 批次获取有效标记
     *
     * @param msgIds 味精 ID
     * @return {@link List }<{@link ChatMessageMark }>
     * @author qingmeng
     * @createTime: 2024/06/07 23:09:54
     */
    public List<ChatMessageMark> getValidMarkByMsgIdBatch(List<Long> msgIds) {
        return lambdaQuery()
                .in(ChatMessageMark::getMsgId, msgIds)
                .eq(ChatMessageMark::getStatus, NormalOrNoEnum.NORMAL.getCode())
                .list();
    }
}
