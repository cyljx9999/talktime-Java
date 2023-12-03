package com.qingmeng.dto.login;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 检查好友
 * @createTime 2023年12月03日 12:47:00
 */
@Data
public class CheckFriendDTO {

    /**
     * 好友ID
     */
    @NotNull
    private Long friendId;

}
