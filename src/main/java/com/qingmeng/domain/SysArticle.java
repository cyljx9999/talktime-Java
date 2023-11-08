package com.qingmeng.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 物品信息表
 * @TableName sys_article
 */
@TableName(value ="sys_article")
@Data
public class SysArticle implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 物品类型 0 改名卡 1徽章 2头像边框
     */
    @TableField(value = "article_type")
    private Integer articleType;

    /**
     * 物品链接
     */
    @TableField(value = "article_url")
    private String articleUrl;

    /**
     * 物品描述
     */
    @TableField(value = "article_describe")
    private String articleDescribe;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    private String isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}