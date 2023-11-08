package com.qingmeng.config.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义线程池配置
 * @createTime 2023年11月08日 14:20:00
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {
    @Resource
    private ThreadPoolProperties threadPoolProperties;


    /**
     * @return {@link Executor }
     * @Description: 任务线程池
     * @author qingmeng
     * @createTime: 2023/11/08 14:21:30
     */
    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(threadPoolProperties.getPrefixName());
        //设置线程池关闭的时候 等待所有的任务完成后再继续销毁其他的bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * @return {@link Executor }
     * @Description: 可见性任务线程池
     * @author qingmeng
     * @createTime: 2023/11/08 14:26:08
     */
    @Bean("visibleTaskExecutor")
    public Executor visibleTaskExecutor() {
        // 1、使用VisibleThreadPoolTaskExecutor作为类
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(threadPoolProperties.getPrefixName());
        //设置线程池关闭的时候 等待所有的任务完成后再继续销毁其他的bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}