package com.qingmeng.vo.user;

import com.qingmeng.annotation.Desensitization;
import com.qingmeng.enums.system.DesensitizationEnum;
import com.qingmeng.enums.user.SexEnum;
import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 个人信息
 * @createTime 2023年11月23日 21:49:00
 */
@Data
public class PersonalInfoVO {
    /**
     * 编号
     */
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
     * 用户手机号
     */
    @Desensitization(type =  DesensitizationEnum.MOBILE_PHONE)
    private String userPhone;

    /**
     * 用户性别 0女 1男 2未知
     * @see SexEnum
     */
    private Integer userSex;

    /**
     * 个人二维码信息
     */
    private String qrcodeUrl;

    /**
     * 修改账号机会
     */
    private Integer alterAccountCount;

}

