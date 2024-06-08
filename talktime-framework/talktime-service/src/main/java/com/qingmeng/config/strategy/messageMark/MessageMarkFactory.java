package com.qingmeng.config.strategy.messageMark;

import com.qingmeng.config.strategy.message.MessageStrategy;
import com.qingmeng.exception.TalkTimeException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息类型工厂类
 * @createTime 2024年06月04日 21:08:00
 */
@Component
public class MessageMarkFactory {
    private final Map<String, MessageMarkStrategy> strategyMap = new ConcurrentHashMap<>();

    public MessageMarkFactory(Map<String, MessageMarkStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    /**
     * 根据消息类型获取具体的处理类

     * @param messageType 消息类型
     * @return {@link MessageStrategy }
     * @author qingmeng
     * @createTime: 2024/06/04 21:09:02
     */
    public MessageMarkStrategy getStrategyWithType(String messageType) {
        MessageMarkStrategy applyFriendStrategy = strategyMap.get(messageType);
        Optional.ofNullable(applyFriendStrategy).orElseThrow(() -> new TalkTimeException("不存在该消息类型"));
        return applyFriendStrategy;
    }
}
