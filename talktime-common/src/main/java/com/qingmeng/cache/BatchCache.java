package com.qingmeng.cache;

import java.util.List;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 批量缓存统一接口
 * @createTime 2023年11月10日 10:30:00
 */
public interface BatchCache<IN, OUT> {
    /**
     * 获取单个
     */
    OUT get(IN req);

    /**
     * 获取批量
     */
    Map<IN, OUT> getBatch(List<IN> req);

    /**
     * 修改删除单个
     */
    void delete(IN req);

    /**
     * 修改删除多个
     */
    void deleteBatch(List<IN> req);
}