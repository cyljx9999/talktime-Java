package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-25 02:36:25
 */
@Data
@TableName("sys_wear_article_record")
public class SysWearArticleRecord implements Serializable {


    private static final long serialVersionUID = 7515858354255677482L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 文章 ID
     */
    private Long articleId;

    /**
     * 物品类型 0徽章 1头像边框
     * @see com.qingmeng.enums.article.ArticleTypeEnum
     */
    private Integer articleType;
}
