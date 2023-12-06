package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户隐私设置表
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-27 10:17:01
 */
@Getter
@Setter
@TableName("sys_user_privacy_setting")
public class SysUserPrivacySetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 账号添加 0 关闭 1 开启
     * @see com.qingmeng.enums.user.CloseOrOpenStatusEnum
     */
    private Integer addByAccount;

    /**
     * 手机号添加 0 关闭 1 开启
     * @see com.qingmeng.enums.user.CloseOrOpenStatusEnum
     */
    private Integer addByPhone;

    /**
     * 群聊添加 0 关闭 1 开启
     * @see com.qingmeng.enums.user.CloseOrOpenStatusEnum
     */
    private Integer addByGroup;

    /**
     * 分享卡片添加 0 关闭 1 开启
     * @see com.qingmeng.enums.user.CloseOrOpenStatusEnum
     */
    private Integer addByCard;

    /**
     * 扫码添加 0 关闭 1 开启
     * @see com.qingmeng.enums.user.CloseOrOpenStatusEnum
     */
    private Integer addByQrcode;

    /**
     * 个性化签名
     */
    private String personalizedSignature;

    /**
     * 拍一拍内容
     */
    private String patContent;
}
