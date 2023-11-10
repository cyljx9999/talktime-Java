package com.qingmeng.strategy.login;

import com.qingmeng.exception.TalkTimeException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 工厂类
 * @createTime 2023年11月11日 00:19:00
 */
@Component
public class LoginFactory {
    private final Map<String, LoginStrategy> strategyMap = new ConcurrentHashMap<>();

    public LoginFactory(Map<String, LoginStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    /**
     *  根据登陆类型获取具体的处理类
     *
     * @param loginType 登陆类型
     * @return {@link LoginStrategy }
     * @author qingmeng
     * @createTime: 2023/11/11 00:21:50
     */
    public LoginStrategy getStrategyWithType(String loginType) {
        LoginStrategy loginStrategy = strategyMap.get(loginType);
        Optional.ofNullable(loginStrategy).orElseThrow(() -> new TalkTimeException("不存在该登陆类型"));
        return loginStrategy;
    }
}
