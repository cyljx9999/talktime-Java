package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 菜单实体类
 * @createTime 2024年06月11日 16:34:00
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -8539903183097198205L;
    /**
     * 主键
     *
     */
    @Null
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级菜单id
     */
    @NotNull
    private Long parentId;

    /**
     * 上级菜单名称
     */
    private String parentName;


    /**
     * 菜单名称
     */
    @NotBlank
    private String menuLabel;

    /**
     * 权限字段
     */
    @NotBlank
    private String menuCode;

    /**
     * 路由名称
     */
    @NotBlank
    private String name;

    /**
     * 路由地址
     */
    @NotBlank
    private String path;

    /**
     * 组件路径
     */
    @NotBlank
    private String url;

    /**
     * 菜单类型 0：目录 1：菜单 2：按钮
     */
    private Integer type;

    /**
     * element-plus图标
     */
    private String elIcon;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Integer isDeleted;

}