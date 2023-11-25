package com.qingmeng.service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-25 02:36:25
 */
public interface SysWearArticleRecordService {

    /**
     * 佩戴物品
     *
     * @param userId    用户 ID
     * @param articleIds 物品 ID
     * @param type      类型
     * @author qingmeng
     * @createTime: 2023/11/25 14:56:44
     */
    void wearArticle(Long userId, List<Long> articleIds, int type);
}
