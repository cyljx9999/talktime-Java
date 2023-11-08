package com.qingmeng.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 线程池属性
 * @createTime 2023年11月08日 14:04:00
 */
@Component
@Data
@ConfigurationProperties(prefix = "thread.pool.executor")
public class ThreadPoolProperties {

    /**
     * 核心线程数量
     */
    private Integer corePoolSize;

    /**
     * 核心线程数
     */
    private Integer maxPoolSize;

    /**
     * 最大线程数
     */
    private Integer queueCapacity;
    /**
     * 队列容量
     */
    private Integer keepAliveSeconds;
    /**
     * 保持活动秒数
     */
    private String prefixName;
}
