package com.qingmeng.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 修改账号
 * @createTime 2023年11月23日 21:42:00
 */
@Data
public class AlterAccountDTO {

    /**
     * 用户账户
     */
    @NotBlank
    @Length(min = 4, max = 10,message = "账号长度为4-10位")
    private String userAccount;

}
