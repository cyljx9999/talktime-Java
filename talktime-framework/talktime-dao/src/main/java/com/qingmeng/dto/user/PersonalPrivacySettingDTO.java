package com.qingmeng.dto.user;

import com.qingmeng.enums.common.CloseOrOpenStatusEnum;
import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 个人隐私设置返回类
 * @createTime 2023年12月02日 10:47:00
 */
@Data
public class PersonalPrivacySettingDTO {

    /**
     * 账号添加 0 关闭 1 开启
     *
     * @see CloseOrOpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer addByAccount;

    /**
     * 手机号添加 0 关闭 1 开启
     *
     * @see CloseOrOpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer addByPhone;

    /**
     * 群聊添加 0 关闭 1 开启
     *
     * @see CloseOrOpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer addByGroup;

    /**
     * 分享卡片添加 0 关闭 1 开启
     *
     * @see CloseOrOpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer addByCard;

    /**
     * 扫码添加 0 关闭 1 开启
     *
     * @see CloseOrOpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer addByQrcode;

    /**
     * 个性化签名
     */
    @Length(max = 20)
    private String personalizedSignature;

    /**
     * 拍一拍内容
     */
    @Length(max = 20)
    private String patContent;
}
