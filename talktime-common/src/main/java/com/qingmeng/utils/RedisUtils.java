package com.qingmeng.utils;

import cn.hutool.core.collection.CollUtil;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description redis工具类
 * @createTime 2023年11月08日 15:36:00
 */
public class RedisUtils {

    private RedisUtils() {}

    private static final StringRedisTemplate REDIS_TEMPLATE = SpringUtils.getBean(StringRedisTemplate.class);

    // -------------------key相关操作---------------------

    /**
     * 删除key
     *
     * @param key 键
     */
    public static void delete(String key) {
        REDIS_TEMPLATE.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys 多个键
     */
    public static void delete(Collection<String> keys) {
        REDIS_TEMPLATE.delete(keys);
    }

    /**
     * 序列化key
     *
     * @param key 键
     * @return 字节数组
     */
    public static byte[] dump(String key) {
        return REDIS_TEMPLATE.dump(key);
    }

    /**
     * 是否存在key
     *
     * @param key 键
     * @return 是否存在
     */
    public static Boolean hasKey(String key) {
        return REDIS_TEMPLATE.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    单位
     * @return 是否成功
     */
    public static Boolean expire(String key, long timeout, TimeUnit unit) {
        return REDIS_TEMPLATE.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     *
     * @param key  键
     * @param date 日期
     * @return 是否成功
     */
    public static Boolean expireAt(String key, Date date) {
        return REDIS_TEMPLATE.expireAt(key, date);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern 正则表达式
     * @return keySet
     */
    public static Set<String> keys(String pattern) {
        return REDIS_TEMPLATE.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否成功
     */
    public static Boolean move(String key, int dbIndex) {
        return REDIS_TEMPLATE.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key 键
     * @return 是否成功
     */
    public static Boolean persist(String key) {
        return REDIS_TEMPLATE.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key  键
     * @param unit 单位
     * @return 剩余时间
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return REDIS_TEMPLATE.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key 键
     * @return 过期时间
     */
    public static Long getExpire(String key) {
        return REDIS_TEMPLATE.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return 键
     */
    public static String randomKey() {
        return REDIS_TEMPLATE.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey 旧的键
     * @param newKey 新的键
     */
    public static void rename(String oldKey, String newKey) {
        REDIS_TEMPLATE.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey 旧的键
     * @param newKey 新的键
     * @return 是否成功
     */
    public static Boolean renameIfAbsent(String oldKey, String newKey) {
        return REDIS_TEMPLATE.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key 键
     * @return 值类型
     */
    public static DataType type(String key) {
        return REDIS_TEMPLATE.type(key);
    }

    // -------------------string相关操作---------------------

    /**
     * 设置指定 key 的值
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, String value) {
        REDIS_TEMPLATE.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    单位
     */
    public static void set(String key, String value, long timeout, TimeUnit unit) {
        REDIS_TEMPLATE.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 批量缓存
     *
     * @param map  数据
     * @param time 时间
     * @author qingmeng
     * @createTime: 2023/11/22 09:44:50
     */
    public static <T> void multiset(Map<String, T> map, long time) {
        Map<String, String> collect = map.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey,
                                (e) -> JsonUtils.toStr(e.getValue())
                        )
                );
        REDIS_TEMPLATE.opsForValue().multiSet(collect);
        map.forEach((key, value) -> {
            expire(key, time,TimeUnit.SECONDS);
        });
    }



    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return REDIS_TEMPLATE.opsForValue().get(key);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 子字符串
     */
    public static String getRange(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key   键
     * @param value 值
     * @return 旧值
     */
    public static String getAndSet(String key, String value) {
        return REDIS_TEMPLATE.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key    键
     * @param offset 偏移量
     * @return 存储在偏移处的原始位值
     */
    public static Boolean getBit(String key, long offset) {
        return REDIS_TEMPLATE.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys 多个键
     * @return 多个值
     */
    public static List<String> multiGet(Collection<String> keys) {
        return REDIS_TEMPLATE.opsForValue().multiGet(keys);
    }

    /**
     * 批量获取
     *
     * @param keys 多个键
     * @return 多个值
     */
    public static <T> List<T> multiGet(Collection<String> keys, Class<T> className) {
        List<String> list = REDIS_TEMPLATE.opsForValue().multiGet(keys);
        if (CollUtil.isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().map(o -> JsonUtils.toBeanOrNull(o, className)).collect(Collectors.toList());
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key    键
     * @param offset 位置
     * @param value  值,true为1, false为0
     * @return 存储在偏移处的原始位值
     */
    public static Boolean setBit(String key, long offset, boolean value) {
        return REDIS_TEMPLATE.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public static void setEx(String key, String value, long timeout, TimeUnit unit) {
        REDIS_TEMPLATE.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   键
     * @param value 值
     * @return 之前已经存在返回false, 不存在返回true
     */
    public static Boolean setIfAbsent(String key, String value) {
        return REDIS_TEMPLATE.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key    键
     * @param value  值
     * @param offset 从指定位置开始覆写
     */
    public static void setRange(String key, String value, long offset) {
        REDIS_TEMPLATE.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key 键
     * @return 长度
     */
    public static Long size(String key) {
        return REDIS_TEMPLATE.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps 多个map
     */
    public static void multiSet(Map<String, String> maps) {
        REDIS_TEMPLATE.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps 多个map
     * @return 之前已经存在返回false, 不存在返回true
     */
    public static Boolean multiSetIfAbsent(Map<String, String> maps) {
        return REDIS_TEMPLATE.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key       键
     * @param increment 自增数值
     * @return 增量后的键值
     */
    public static Long incrBy(String key, long increment) {
        return REDIS_TEMPLATE.opsForValue().increment(key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key       键
     * @param increment 自增数值
     * @return 增量后的键值
     */
    public static Double incrByFloat(String key, double increment) {
        return REDIS_TEMPLATE.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key   键
     * @param value 值
     * @return 增量后的键值
     */
    public static Integer append(String key, String value) {
        return REDIS_TEMPLATE.opsForValue().append(key, value);
    }

    // -------------------hash相关操作-------------------------

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    public static Object hGet(String key, String field) {
        return REDIS_TEMPLATE.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key 键
     * @return map
     */
    public static Map<Object, Object> hGetAll(String key) {
        return REDIS_TEMPLATE.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    键
     * @param fields 字段
     * @return list
     */
    public static List<Object> hMultiGet(String key, Collection<Object> fields) {
        return REDIS_TEMPLATE.opsForHash().multiGet(key, fields);
    }

    /**
     * 增加一个哈希表字段
     *
     * @param key     键
     * @param hashKey hash键
     * @param value   hash值
     */
    public static void hPut(String key, String hashKey, String value) {
        REDIS_TEMPLATE.opsForHash().put(key, hashKey, value);
    }

    /**
     * 增加多个哈希表字段
     *
     * @param key  键
     * @param maps 多个map
     */
    public static void hPutAll(String key, Map<String, String> maps) {
        REDIS_TEMPLATE.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key     键
     * @param hashKey hash键
     * @param value   值
     * @return 是否成功
     */
    public static Boolean hPutIfAbsent(String key, String hashKey, String value) {
        return REDIS_TEMPLATE.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key    键
     * @param fields 多个值（可选）
     * @return 删除数量
     */
    public static Long hDelete(String key, Object... fields) {
        return REDIS_TEMPLATE.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   键
     * @param field 字段
     * @return 是否存在
     */
    public static Boolean hExists(String key, String field) {
        return REDIS_TEMPLATE.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key       键
     * @param field     字段
     * @param increment 自增数值
     * @return 增量后的值
     */
    public static Long hIncrBy(String key, Object field, long increment) {
        return REDIS_TEMPLATE.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key   键
     * @param field 字段
     * @param delta 自增数值
     * @return 增量后的值
     */
    public static Double hIncrByFloat(String key, Object field, double delta) {
        return REDIS_TEMPLATE.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key 键
     * @return 所有字段
     */
    public static Set<Object> hKeys(String key) {
        return REDIS_TEMPLATE.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key 键
     * @return 哈希表长度
     */
    public static Long hSize(String key) {
        return REDIS_TEMPLATE.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key 键
     * @return 所有值
     */
    public static List<Object> hValues(String key) {
        return REDIS_TEMPLATE.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     键
     * @param options 扫描选项
     * @return 游标
     */
    public static Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return REDIS_TEMPLATE.opsForHash().scan(key, options);
    }

    // ------------------------list相关操作----------------------------

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    public static String lIndex(String key, long index) {
        return REDIS_TEMPLATE.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   键
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return 多个值
     */
    public static List<String> lRange(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForList().range(key, start, end);
    }

    /**
     * 存储在list头部
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public static Long lLeftPush(String key, String value) {
        return REDIS_TEMPLATE.opsForList().leftPush(key, value);
    }

    /**
     * 存储在list头部
     *
     * @param key   键
     * @param value 多个值（可选）
     * @return 操作后列表的长度
     */
    public static Long lLeftPushAll(String key, String... value) {
        return REDIS_TEMPLATE.opsForList().leftPushAll(key, value);
    }

    /**
     * 存储在list头部
     *
     * @param key   键
     * @param value 多个值
     * @return 操作后列表的长度
     */
    public static Long lLeftPushAll(String key, Collection<String> value) {
        return REDIS_TEMPLATE.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public static Long lLeftPushIfPresent(String key, String value) {
        return REDIS_TEMPLATE.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key   键
     * @param pivot pivot
     * @param value 值
     * @return 操作后列表的长度
     */
    public static Long lLeftPush(String key, String pivot, String value) {
        return REDIS_TEMPLATE.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public static Long lRightPush(String key, String value) {
        return REDIS_TEMPLATE.opsForList().rightPush(key, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   键
     * @param value 多个值（可选）
     * @return 操作后列表的长度
     */
    public static Long lRightPushAll(String key, String... value) {
        return REDIS_TEMPLATE.opsForList().rightPushAll(key, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   键
     * @param value 多个值
     * @return 操作后列表的长度
     */
    public static Long lRightPushAll(String key, Collection<String> value) {
        return REDIS_TEMPLATE.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public static Long lRightPushIfPresent(String key, String value) {
        return REDIS_TEMPLATE.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key   键
     * @param pivot pivot
     * @param value 值
     * @return 操作后列表的长度
     */
    public static Long lRightPush(String key, String pivot, String value) {
        return REDIS_TEMPLATE.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   键
     * @param index 位置
     * @param value 值
     */
    public static void lSet(String key, long index, String value) {
        REDIS_TEMPLATE.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key 键
     * @return 删除的元素
     */
    public static String lLeftPop(String key) {
        return REDIS_TEMPLATE.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     键
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 第一个元素
     */
    public static String lLeftPop(String key, long timeout, TimeUnit unit) {
        return REDIS_TEMPLATE.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key 键
     * @return 删除的元素
     */
    public static String lRightPop(String key) {
        return REDIS_TEMPLATE.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     键
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 最后一个元素
     */
    public static String lRightPop(String key, long timeout, TimeUnit unit) {
        return REDIS_TEMPLATE.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      来源key
     * @param destinationKey 目标key
     * @return 最后一个元素
     */
    public static String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return REDIS_TEMPLATE.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      来源key
     * @param destinationKey 目标key
     * @param timeout        过期时间
     * @param unit           单位
     * @return 值
     */
    public static String lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return REDIS_TEMPLATE.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key   键
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value 值
     * @return list长度
     */
    public static Long lRemove(String key, long index, String value) {
        return REDIS_TEMPLATE.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结果下标
     */
    public static void lTrim(String key, long start, long end) {
        REDIS_TEMPLATE.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return list长度
     */
    public static Long lLen(String key) {
        return REDIS_TEMPLATE.opsForList().size(key);
    }

    // --------------------set相关操作--------------------------

    /**
     * set添加元素
     *
     * @param key    键
     * @param values 多个值（可选）
     * @return 添加到集合中的元素数量，不包括已经存在于集合中的所有元素
     */
    public static Long sAdd(String key, String... values) {
        return REDIS_TEMPLATE.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key    键
     * @param values 多个值
     * @return 从集合中删除的成员数，不包括非现有成员。
     */
    public static Long sRemove(String key, Object... values) {
        return REDIS_TEMPLATE.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key 键
     * @return 随机元素
     */
    public static String sPop(String key) {
        return REDIS_TEMPLATE.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key     键
     * @param value   值
     * @param destKey 目标key
     * @return 如果元素被移动，则为true。 如果元素不是源成员且未执行任何操作，则为false。
     */
    public static Boolean sMove(String key, String value, String destKey) {
        return REDIS_TEMPLATE.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key 键
     * @return set长度
     */
    public static Long sSize(String key) {
        return REDIS_TEMPLATE.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    public static Boolean sIsMember(String key, Object value) {
        return REDIS_TEMPLATE.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key      键一
     * @param otherKey 键二
     * @return 交集
     */
    public static Set<String> sIntersect(String key, String otherKey) {
        return REDIS_TEMPLATE.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key       键一
     * @param otherKeys 多个其他键
     * @return 交集
     */
    public static Set<String> sIntersect(String key, Collection<String> otherKeys) {
        return REDIS_TEMPLATE.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return set长度
     */
    public static Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return REDIS_TEMPLATE.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key       键一
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public static Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return REDIS_TEMPLATE.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @return 并集
     */
    public static Set<String> sUnion(String key, String otherKeys) {
        return REDIS_TEMPLATE.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @return 并集
     */
    public static Set<String> sUnion(String key, Collection<String> otherKeys) {
        return REDIS_TEMPLATE.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public static Long sUnionAndStore(String key, String otherKey, String destKey) {
        return REDIS_TEMPLATE.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public static Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return REDIS_TEMPLATE.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      键一
     * @param otherKey 键二
     * @return 差集
     */
    public static Set<String> sDifference(String key, String otherKey) {
        return REDIS_TEMPLATE.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @return 差集
     */
    public static Set<String> sDifference(String key, Collection<String> otherKeys) {
        return REDIS_TEMPLATE.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public static Long sDifference(String key, String otherKey, String destKey) {
        return REDIS_TEMPLATE.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public static Long sDifference(String key, Collection<String> otherKeys, String destKey) {
        return REDIS_TEMPLATE.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key 键
     * @return 所有元素
     */
    public static Set<String> setMembers(String key) {
        return REDIS_TEMPLATE.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key 键
     * @return 随机元素
     */
    public static String sRandomMember(String key) {
        return REDIS_TEMPLATE.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key   键
     * @param count 数量
     * @return 随机元素list
     */
    public static List<String> sRandomMembers(String key, long count) {
        return REDIS_TEMPLATE.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key   键
     * @param count 数量
     * @return 随机元素set
     */
    public static Set<String> sDistinctRandomMembers(String key, long count) {
        return REDIS_TEMPLATE.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 获取Cursor
     *
     * @param key     键
     * @param options 扫描选项
     * @return 游标
     */
    public static Cursor<String> sScan(String key, ScanOptions options) {
        return REDIS_TEMPLATE.opsForSet().scan(key, options);
    }

    // ------------------zSet相关操作--------------------------------

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return 是否成功
     */
    public static Boolean zAdd(String key, String value, double score) {
        return REDIS_TEMPLATE.opsForZSet().add(key, value, score);
    }

    /**
     * 添加多个元素
     *
     * @param key    键
     * @param values 多个值
     * @return 添加的数量
     */
    public static Long zAdd(String key, Set<TypedTuple<String>> values) {
        return REDIS_TEMPLATE.opsForZSet().add(key, values);
    }

    /**
     * 移除元素
     *
     * @param key    键
     * @param values 多个值（可选）
     * @return 删除的数量
     */
    public static Long zRemove(String key, Object... values) {
        return REDIS_TEMPLATE.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   键
     * @param value 值
     * @param delta delta
     * @return 成员的新分数（双精度浮点数），表示为字符串
     */
    public static Double zIncrementScore(String key, String value, double delta) {
        return REDIS_TEMPLATE.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public static Long zRank(String key, Object value) {
        return REDIS_TEMPLATE.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public static Long zReverseRank(String key, Object value) {
        return REDIS_TEMPLATE.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public static Set<String> zRange(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public static Set<TypedTuple<String>> zRangeWithScores(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public static Set<String> zRangeByScore(String key, double min, double max) {
        return REDIS_TEMPLATE.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public static Set<TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max) {
        return REDIS_TEMPLATE.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public static Set<TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public static Set<String> zReverseRange(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public static Set<TypedTuple<String>> zReverseRangeWithScores(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public static Set<String> zReverseRangeByScore(String key, double min, double max) {
        return REDIS_TEMPLATE.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public static Set<TypedTuple<String>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        return REDIS_TEMPLATE.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public static Set<String> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 数量
     */
    public static Long zCount(String key, double min, double max) {
        return REDIS_TEMPLATE.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public static Long zSize(String key) {
        return REDIS_TEMPLATE.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public static Long zCard(String key) {
        return REDIS_TEMPLATE.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   键
     * @param value 值
     * @return score值
     */
    public static Double zScore(String key, Object value) {
        return REDIS_TEMPLATE.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 值
     */
    public static Long zRemoveRange(String key, long start, long end) {
        return REDIS_TEMPLATE.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Long zRemoveRangeByScore(String key, double min, double max) {
        return REDIS_TEMPLATE.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public static Long zUnionAndStore(String key, String otherKey, String destKey) {
        return REDIS_TEMPLATE.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public static Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return REDIS_TEMPLATE.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public static Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return REDIS_TEMPLATE.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 交集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public static Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return REDIS_TEMPLATE.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取Cursor
     *
     * @param key     键
     * @param options 扫描选项
     * @return 游标
     */
    public static Cursor<TypedTuple<String>> zScan(String key, ScanOptions options) {
        return REDIS_TEMPLATE.opsForZSet().scan(key, options);
    }
}