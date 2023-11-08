package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Getter
@Setter
@TableName("sys_user_group_role")
public class SysUserGroupRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 群组角色id
     */
    private Long groupRoleId;

    /**
     * 群聊房间id
     */
    private Long roomGroupId;
}
