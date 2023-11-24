package com.qingmeng.vo.article;

import com.qingmeng.enums.article.ArticleTypeEnum;
import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月24日 20:43:00
 */
@Data
public class SysArticleVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 物品类型 0徽章 1头像边框
     * @see ArticleTypeEnum
     */
    private Integer articleType;

    /**
     * 物品链接
     */
    private String articleUrl;

    /**
     * 物品描述
     */
    private String articleDescribe;
}
