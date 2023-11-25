package com.qingmeng.vo.article;

import lombok.Data;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月24日 21:06:00
 */
@Data
public class SysArticleListVO {

    /**
     * 物品列表列表
     */
    private List<SysArticleVO> articleListList;

    /**
     * 已拥有的物品ids
     */
    private List<Long> alreadyHadIds;

    /**
     * 佩戴物品 ID
     */
    private List<Long> wearArticleIds;

}
