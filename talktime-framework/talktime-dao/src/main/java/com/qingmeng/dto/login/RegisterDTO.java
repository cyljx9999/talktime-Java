package com.qingmeng.dto.login;

import com.qingmeng.valid.AddGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 注册类
 * @createTime 2023年11月12日 13:47:00
 */
@Data
public class RegisterDTO {
    /**
     * 用户名
     */
    @NotBlank(groups = {AddGroup.class})
    @Length(min = 1, max = 10, groups = {AddGroup.class})
    private String userName;

    /**
     * 账号
     */
    @NotBlank(groups = {AddGroup.class})
    @Length(min = 4, max = 10, groups = {AddGroup.class})
    private String account;

    /**
     * 密码
     */
    @NotBlank(groups = {AddGroup.class})
    private String password;

    /**
     * 手机
     */
    @NotBlank(groups = {AddGroup.class})
    @Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$",
            message = "手机号格式不正确",
            groups = {AddGroup.class})
    private String phone;

    /**
     * 手机验证码
     */
    @NotBlank(groups = {AddGroup.class})
    private String phoneCode;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;
}
