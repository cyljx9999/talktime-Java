package com.qingmeng.config.strategy.applyFriend;

import com.qingmeng.exception.TalkTimeException;
import com.qingmeng.config.strategy.login.LoginStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 工厂类
 * @createTime 2023年11月27日 15:34:00
 */
@Component
public class ApplyFriendFactory {
    private final Map<String, ApplyFriendStrategy> strategyMap = new ConcurrentHashMap<>();

    public ApplyFriendFactory(Map<String, ApplyFriendStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    /**
     *  根据渠道类型获取具体的处理类
     *
     * @param channelType 渠道类型
     * @return {@link LoginStrategy }
     * @author qingmeng
     * @createTime: 2023/11/11 00:21:50
     */
    public ApplyFriendStrategy getStrategyWithType(String channelType) {
        ApplyFriendStrategy applyFriendStrategy = strategyMap.get(channelType);
        Optional.ofNullable(applyFriendStrategy).orElseThrow(() -> new TalkTimeException("不存在该渠道类型"));
        return applyFriendStrategy;
    }
}
