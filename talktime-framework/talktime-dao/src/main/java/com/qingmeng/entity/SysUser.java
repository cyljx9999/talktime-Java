package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.system.LogicDeleteEnum;
import com.qingmeng.enums.user.AccountStatusEnum;
import com.qingmeng.enums.user.SexEnum;
import com.qingmeng.enums.user.UsageStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 4875299392817609503L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户性别 0女 1男
     * @see SexEnum
     */
    private Integer userSex;

    /**
     * 个人二维码信息
     */
    private String qrcodeUrl;

    /**
     * 0 不在线 1在线
     * @see UsageStatusEnum
     */
    private Integer onlineStatus;

    /**
     * 最后一次上下线时间
     */
    private Date lastOperateTime;

    /**
     * 账号状态 0正常 1封禁
     * @see  AccountStatusEnum
     */
    private Integer accountStatus;

    /**
     * 修改账号机会
     */
    private Integer alterAccountCount;

    /**
     * 物品id
     */
    private Long articleId;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * ip归属地
     */
    private String ipLocation;

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
     * 逻辑删除 0未删除 1删除
     * @see LogicDeleteEnum
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}
