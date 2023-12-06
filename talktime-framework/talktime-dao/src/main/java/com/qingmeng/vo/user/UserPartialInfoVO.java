package com.qingmeng.vo.user;

import com.qingmeng.enums.user.SexEnum;
import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户部分信息
 * @createTime 2023年12月06日 10:46:00
 */
@Data
public class UserPartialInfoVO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户帐户
     */
    private String userAccount;

    /**
     * 用户性别 0女 1男 2未知
     * @see SexEnum
     */
    private Integer userSex;

    /**
     * 位置
     */
    private String location;

}
