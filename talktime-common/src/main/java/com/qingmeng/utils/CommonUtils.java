package com.qingmeng.utils;

import java.util.Arrays;
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
        return ids.stream().sorted().map(Objects::toString).collect(Collectors.joining("-"));
    }

    /**
     * 获取好友设置缓存密钥
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/02 10:00:04
     */
    public static String getFriendSettingCacheKey(Long userId, Long friendId) {
        return getKeyBySort(Arrays.asList(userId, friendId)) + ":" + userId;
    }

}
