package com.qingmeng.dto.user;

import com.qingmeng.enums.user.SexEnum;
import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 修改个人信息参数类
 * @createTime 2023年12月02日 10:33:00
 */
@Data
public class AlterPersonalInfoDTO {
    /**
     * 用户名
     */
    @Length(min = 1, max = 10)
    private String userName;

    /**
     * 用户头像
     */
    @URL
    private String userAvatar;


    /**
     * 用户手机号
     */
    @Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$", message = "手机号格式不正确")
    private String userPhone;

    /**
     * 用户性别 0女 1男 2未知
     *
     * @see SexEnum
     */
    @IntListValue(values = {0, 1, 2})
    private Integer userSex;

    private String encryptStr;
}
