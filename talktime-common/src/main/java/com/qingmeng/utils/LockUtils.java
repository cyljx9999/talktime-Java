package com.qingmeng.utils;

import com.qingmeng.enums.ResultEnum;
import com.qingmeng.exception.TalkTimeException;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 分布式锁工具类
 * @createTime 2023年11月08日 20:53:00
 */
@Service
public class LockUtils {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 使用分布式锁执行带有异常抛出的逻辑。
     *
     * @param key      锁的唯一标识
     * @param waitTime 等待获取锁的最大时间
     * @param unit     时间单位
     * @param supplier 带异常抛出的逻辑执行器
     * @param <T>      返回值的类型
     * @return 执行逻辑的结果
     * @throws Throwable 如果执行逻辑中抛出异常
     */
    public <T> T executeWithLockThrows(String key, int waitTime, TimeUnit unit, SupplierThrow<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new TalkTimeException(ResultEnum.REQUEST_FREQUENT);
        }
        try {
            // 执行锁内的代码逻辑
            return supplier.get();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 使用分布式锁执行逻辑（不限制等待时间）。
     *
     * @param key      锁的唯一标识
     * @param supplier 逻辑执行器
     * @param <T>      返回值的类型
     * @return 执行逻辑的结果
     */
    @SneakyThrows
    public <T> T executeWithLock(String key, Supplier<T> supplier) {
        return executeWithLockThrows(key, -1, TimeUnit.MILLISECONDS, supplier::get);
    }

    /**
     * 函数式接口，用于执行逻辑时允许抛出异常。
     *
     * @param <T> 返回值的类型
     */
    @FunctionalInterface
    public interface SupplierThrow<T> {
        /**
         * Gets a result.
         *
         * @return a result
         * @throws Throwable 如果执行逻辑中抛出异常
         */
        T get() throws Throwable;
    }
}
