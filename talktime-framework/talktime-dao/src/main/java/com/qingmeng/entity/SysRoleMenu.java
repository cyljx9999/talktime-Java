package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 角色菜单关联表
 * @createTime 2024年06月11日 16:35:03
 */
@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 8995377459064223963L;

    @TableId(type= IdType.AUTO)
    private Long roleMenuId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

}
