package com.qingmeng.service;

import com.qingmeng.dto.article.WearArticleDTO;
import com.qingmeng.vo.article.SysArticleListVO;

/**
 * <p>
 * 物品信息表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysArticleService {

    /**
     * 获取物品展示列表
     *
     * @return {@link SysArticleListVO }
     * @author qingmeng
     * @createTime: 2023/11/24 21:10:49
     */
    SysArticleListVO getArticleDisplayList();

    /**
     * 佩戴头像边框
     *
     * @param userId         用户 ID
     * @param wearArticleDTO DTO
     * @author qingmeng
     * @createTime: 2023/11/24 22:01:54
     */
    void wearHeadBorder(Long userId, WearArticleDTO wearArticleDTO);

    /**
     * 佩戴徽章
     *
     * @param userId         用户 ID
     * @param wearArticleDTO  DTO
     * @author qingmeng
     * @createTime: 2023/11/24 22:30:18
     */
    void wearBadge(Long userId, WearArticleDTO wearArticleDTO);
}
