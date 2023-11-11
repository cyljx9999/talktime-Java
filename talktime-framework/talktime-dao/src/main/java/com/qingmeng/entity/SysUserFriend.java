package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qingmeng.enums.user.FriendStausEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户好友表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Data
@TableName("sys_user_friend")
public class SysUserFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 好友id
     */
    private Long friendId;

    /**
     * 好友状态 0正常 1删除
     * @see FriendStausEnum
     */
    private Integer friendStatus;

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
}
