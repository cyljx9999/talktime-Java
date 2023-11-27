package com.qingmeng.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 通用工具
 * @createTime 2023年11月27日 15:09:00
 */
public class CommonUtils {

    /**
     * 按排序获取密钥
     *
     * @param ids IDS
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/27 15:13:46
     */
    public static String getKeyBySort(List<Long> ids){
        return ids.stream().sorted().map(Objects::toString).collect(Collectors.joining(","));
    }

}
