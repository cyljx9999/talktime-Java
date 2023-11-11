package com.qingmeng.domain.dto.login;

import com.qingmeng.valid.AccountGroup;
import com.qingmeng.valid.PhoneGroup;
import com.qingmeng.valid.custom.StingListValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆参数类
 * @createTime 2023年11月10日 22:15:00
 */
@Data
public class LoginParamDTO {
    /**
     * 账号
     */
    @NotBlank(groups = {AccountGroup.class})
    private String account;

    /**
     * 密码
     */
    @NotBlank(groups = {AccountGroup.class})
    private String password;

    /**
     * 验证码
     */
    @NotBlank(groups = {AccountGroup.class})
    private String code;

    /**
     * 是否使用  记住我  模式
     */
    @NotNull(groups = {PhoneGroup.class, AccountGroup.class})
    private Boolean flag;

    /**
     * 登录设备类型
     */
    @NotBlank(groups = {PhoneGroup.class, AccountGroup.class})
    private String loginType;

    /**
     * 登录方法
     * @see com.qingmeng.enums.LoginMethodEnum
     */
    @NotNull(groups = {PhoneGroup.class, AccountGroup.class})
    @StingListValue(values = {"account", "password"}, groups = {PhoneGroup.class, AccountGroup.class})
    private String loginMethod;

    /**
     * 手机
     */
    @NotBlank(groups = {PhoneGroup.class})
    private String phone;

    /**
     * 手机认证码
     */
    @NotBlank(groups = {PhoneGroup.class})
    private String phoneCode;

    private String encryptStr;
}
