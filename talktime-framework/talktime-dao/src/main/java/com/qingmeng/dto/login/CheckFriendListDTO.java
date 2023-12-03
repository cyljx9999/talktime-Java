package com.qingmeng.dto.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 检查好友
 * @createTime 2023年12月03日 12:47:00
 */
@Data
public class CheckFriendListDTO {

    /**
     * 好友ID列表
     */
    @NotEmpty
    private List<Long> friendIdList;

}
