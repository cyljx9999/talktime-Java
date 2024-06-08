package com.qingmeng.config.strategy.messageMark;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息标记
 * @createTime 2024年06月08日 13:31:00
 */
public interface MessageMarkStrategy {

    /**
     * 标记
     *
     * @param userId 用户 ID
     * @param msgId  消息 ID
     * @author qingmeng
     * @createTime: 2024/06/08 13:32:32
     */
    void mark(Long userId, Long msgId);


    /**
     * 取消标记
     *
     * @param userId 用户 ID
     * @param msgId  消息 ID
     * @author qingmeng
     * @createTime: 2024/06/08 13:32:34
     */
    void cancelMark(Long userId, Long msgId);
}
