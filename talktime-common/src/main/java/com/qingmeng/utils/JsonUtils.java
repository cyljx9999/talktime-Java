package com.qingmeng.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description json工具类
 * @createTime 2023年11月10日 10:24:00
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 JSON 字符串转换为指定类型的对象。
     *
     * @param str JSON 字符串
     * @param clz 目标对象的类型
     * @param <T> 目标对象的泛型
     * @return 转换后的对象
     * @throws UnsupportedOperationException 如果转换过程中发生异常
     */
    public static <T> T toObj(String str, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将对象转换为 JSON 字符串。
     *
     * @param t 待转换的对象
     * @return 转换后的 JSON 字符串
     * @throws UnsupportedOperationException 如果转换过程中发生异常
     */
    public static String toStr(Object t) {
        try {
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将 JSON 字符串转换为指定类型的对象，如果字符串为 null，则返回 null。
     *
     * @param json   JSON 字符串
     * @param tClass 目标对象的类型
     * @param <T>    目标对象的泛型
     * @return 转换后的对象，如果 JSON 字符串为 null，则返回 null
     */
    static <T> T toBeanOrNull(String json, Class<T> tClass) {
        return json == null ? null : JsonUtils.toObj(json, tClass);
    }

    /**
     * 列出
     *
     * @param str str
     * @return {@link List }<{@link T }>
     * @author qingmeng
     * @createTime: 2023/11/20 15:43:10
     */
    public static <T> List<T> toList(String str) {
        try {
            return OBJECT_MAPPER.readValue(str, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 对象转List集合
     *
     * @param obj   对象
     * @param clazz 具体的类型
     * @return {@link List }<{@link T }>
     * @author qingmeng
     * @createTime: 2023/12/03 11:13:14
     */
    public static <T> List<T> objToList(Object obj, Class<T> clazz) {
        if (obj == null) {
            return Collections.emptyList();
        }

        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            List<T> resultList = new ArrayList<>(list.size());
            for (Object item : list) {
                if (clazz.isInstance(item)) {
                    resultList.add(clazz.cast(item));
                } else {
                    throw new IllegalArgumentException("List contains elements that are not of type " + clazz.getName());
                }
            }
            return resultList;
        } else {
            throw new IllegalArgumentException("The provided object is not a List");
        }
    }
}
