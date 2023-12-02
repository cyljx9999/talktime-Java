package com.qingmeng.vo.user;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 个人隐私设置返回类
 * @createTime 2023年12月02日 10:47:00
 */
@Data
public class PersonalPrivacySettingVO {
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
}
