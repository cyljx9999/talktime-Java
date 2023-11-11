package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户物品关联表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Data
@TableName("sys_user_article")
public class SysUserArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 物品id
     */
    private Long articleId;

    /**
     * 用户id
     */
    private Long userId;
}
