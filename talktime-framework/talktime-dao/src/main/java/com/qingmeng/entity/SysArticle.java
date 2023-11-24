package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.article.ArticleTypeEnum;
import com.qingmeng.enums.system.LogicDeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 物品信息表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Data
@TableName("sys_article")
public class SysArticle implements Serializable {

    private static final long serialVersionUID = 1561692133079259860L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 逻辑删除
     * @see LogicDeleteEnum
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
