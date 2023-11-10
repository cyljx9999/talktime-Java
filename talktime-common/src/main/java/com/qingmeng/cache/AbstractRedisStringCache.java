package com.qingmeng.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.qingmeng.utils.RedisUtils;
import org.springframework.data.util.Pair;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description redis string类型的批量缓存框架
 * @createTime 2023年11月10日 10:31:00
 */
public abstract class AbstractRedisStringCache<IN, OUT> implements BatchCache<IN, OUT> {

    private final Class<OUT> outClass;

    /**
     * 构造函数，用于获取泛型参数的类型信息。
     */
    protected AbstractRedisStringCache() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.outClass = (Class<OUT>) genericSuperclass.getActualTypeArguments()[1];
    }

    /**
     * 根据输入对象获取缓存的键。
     *
     * @param req 输入对象
     * @return 缓存键
     */
    protected abstract String getKey(IN req);

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return 过期时间（秒）
     */
    protected abstract Long getExpireSeconds();

    /**
     * 批量加载缓存数据。
     *
     * @param req 批量请求列表
     * @return 加载的缓存数据映射
     */
    protected abstract Map<IN, OUT> load(List<IN> req);

    /**
     * 获取单个缓存数据。
     *
     * @param param 输入对象
     * @return 缓存数据
     */
    @Override
    public OUT get(IN param) {
        return getBatch(Collections.singletonList(param)).get(param);
    }

    /**
     * 批量获取缓存数据。
     *
     * @param params 批量请求列表
     * @return 缓存数据映射
     */
    @Override
    public Map<IN, OUT> getBatch(List<IN> params) {
        if (CollectionUtil.isEmpty(params)) {
            return new HashMap<>();
        }
        //去重
        params = params.stream().distinct().collect(Collectors.toList());
        //组装key
        List<String> keys = params.stream().map(this::getKey).collect(Collectors.toList());
        //批量get
        List<OUT> valueList = RedisUtils.multiGet(keys, outClass);
        //差集计算
        List<IN> loadReqs = new ArrayList<>();
        for (int i = 0; i < valueList.size(); i++) {
            if (Objects.isNull(valueList.get(i))) {
                loadReqs.add(params.get(i));
            }
        }
        Map<IN, OUT> load = new HashMap<>();
        //不足的重新加载进redis
        if (CollectionUtil.isNotEmpty(loadReqs)) {
            //批量load
            load = load(loadReqs);
            Map<String, OUT> loadMap = load.entrySet().stream()
                    .map(a -> Pair.of(getKey(a.getKey()), a.getValue()))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
            RedisUtils.multiset(loadMap, getExpireSeconds());
        }

        //组装最后的结果
        Map<IN, OUT> resultMap = new HashMap<>();
        for (int i = 0; i < params.size(); i++) {
            IN in = params.get(i);
            OUT out = Optional.ofNullable(valueList.get(i))
                    .orElse(load.get(in));
            resultMap.put(in, out);
        }
        return resultMap;
    }

    /**
     * 删除单个缓存数据。
     *
     * @param param 输入对象
     */
    @Override
    public void delete(IN param) {
        deleteBatch(Collections.singletonList(param));
    }

    /**
     * 批量删除缓存数据。
     *
     * @param params 批量请求列表
     */
    @Override
    public void deleteBatch(List<IN> params) {
        List<String> keys = params.stream().map(this::getKey).collect(Collectors.toList());
        RedisUtils.delete(keys);
    }
}

