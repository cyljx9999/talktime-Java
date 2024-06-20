package com.qingmeng.vo.user;

import com.qingmeng.enums.common.CloseOrOpenStatusEnum;
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
     * @see CloseOrOpenStatusEnum
     */
    private Integer addByAccount;

    /**
     * 手机号添加 0 关闭 1 开启
     * @see CloseOrOpenStatusEnum
     */
    private Integer addByPhone;

    /**
     * 群聊添加 0 关闭 1 开启
     * @see CloseOrOpenStatusEnum
     */
    private Integer addByGroup;

    /**
     * 分享卡片添加 0 关闭 1 开启
     * @see CloseOrOpenStatusEnum
     */
    private Integer addByCard;

    /**
     * 扫码添加 0 关闭 1 开启
     * @see CloseOrOpenStatusEnum
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


    /**
     * 账号搜索 0 关闭 1 开启
     */
    private Integer findByAccount;

    /**
     * 手机号搜索 0 关闭 1 开启
     */
    private Integer findByPhone;
}
