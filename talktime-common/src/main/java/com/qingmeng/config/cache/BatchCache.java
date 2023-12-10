package com.qingmeng.config.cache;

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
     * 获取单个缓存数据。
     *
     * @param param 输入对象
     * @return 缓存数据
     */
    OUT get(IN param);

    /**
     * 批量获取缓存数据。
     *
     * @param params 批量请求列表
     * @return 缓存数据映射
     */
    Map<IN, OUT> getBatch(List<IN> params);

    /**
     * 删除单个缓存数据。
     *
     * @param param 输入对象
     */
    void delete(IN param);

    /**
     * 批量删除缓存数据。
     *
     * @param params 批量请求列表
     */
    void deleteBatch(List<IN> params);
}