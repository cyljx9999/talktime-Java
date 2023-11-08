package com.qingmeng.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 打印信息的线程池
 * @createTime 2023年11月08日 14:23:00
 */
@Slf4j
public class VisibleThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = 4065232330948855081L;

    private void log(String method){
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        log.info("线程池：{}, 执行方法：{},任务数量 [{}], 完成任务数量 [{}], 活跃线程数 [{}], 队列长度 [{}]",
                this.getThreadNamePrefix(),
                method,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

    @Override
    public void execute(Runnable task) {
        log("execute");
        super.execute(task);
    }



    @Override
    public Future<?> submit(Runnable task) {
        log("submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        log("submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        log("submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        log("submitListenable");
        return super.submitListenable(task);
    }
}