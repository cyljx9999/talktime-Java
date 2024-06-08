package com.qingmeng.service;

/**
 * <p>
 * 用户物品关联表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysUserArticleService{

    /**
     * 物品接收
     *
     * @param fromUserId 从用户 ID
     * @param itemId     商品 ID
     * @param string     字符串
     * @author qingmeng
     * @createTime: 2024/06/08 16:42:24
     */
    void itemReceive(Long fromUserId, Long itemId, String string);
}
