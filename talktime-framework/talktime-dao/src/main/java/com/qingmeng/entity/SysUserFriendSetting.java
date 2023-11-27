package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingmeng.enums.system.LogicDeleteEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:17:00
 */
@Getter
@Setter
@TableName("sys_user_friend_setting")
public class SysUserFriendSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 昵称
     */
    private String nickName;

    /**
     * 好友状态 0正常 1拉黑
     * @see com.qingmeng.enums.user.FriendStausEnum
     */
    private Integer friendStatus;


    /**
     * 添加渠道
     */
    private String addChannel;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

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
