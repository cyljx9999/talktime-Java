package com.qingmeng.dto.login;

import com.qingmeng.enums.user.LoginMethodEnum;
import com.qingmeng.valid.AccountGroup;
import com.qingmeng.valid.PhoneGroup;
import com.qingmeng.valid.custom.StringListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @Length(min = 4, max = 10, groups = {AccountGroup.class})
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
     * 验证码绑定id
     */
    @NotBlank(groups = {AccountGroup.class})
    private String codeId;

    /**
     * 是否使用  记住我  模式
     */
    @NotNull(groups = {PhoneGroup.class, AccountGroup.class})
    private Boolean flag;

    /**
     * 登录设备类型
     * @see com.qingmeng.enums.user.LoginDeviceEnum
     */
    @NotBlank(groups = {PhoneGroup.class, AccountGroup.class})
    @StringListValue(values = {"pc", "h5", "app", "miniProgram"}, groups = {PhoneGroup.class, AccountGroup.class})
    private String loginType;

    /**
     * 登录方法
     * @see LoginMethodEnum
     */
    @NotNull(groups = {PhoneGroup.class, AccountGroup.class})
    @StringListValue(values = {"account", "phone"}, groups = {PhoneGroup.class, AccountGroup.class})
    private String loginMethod;

    /**
     * 手机
     */
    @NotBlank(groups = {PhoneGroup.class})
    @Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$",
            message = "手机号格式不正确",
            groups = {PhoneGroup.class})
    private String phone;

    /**
     * 手机认证码
     */
    @NotBlank(groups = {PhoneGroup.class})
    private String phoneCode;

    private String encryptStr;
}
