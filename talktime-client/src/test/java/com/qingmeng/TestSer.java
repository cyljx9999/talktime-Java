package com.qingmeng;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月08日 14:27:00
 */
@Slf4j
@Component
public class TestSer {
    @Async("taskExecutor")
    public void doAsync() {
        log.info("== async start==");
        log.info("线程{}执行代码逻辑",Thread.currentThread().getName());
        log.info("== async end==");
    }

    @Async("visibleTaskExecutor")
    public void visibleAsync() {
        log.info("== async start==");
        log.info("线程{}执行代码逻辑",Thread.currentThread().getName());
        log.info("== async end==");
    }
}
