package com.qingmeng.vo.user;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 检查好友
 * @createTime 2023年12月03日 12:45:00
 */
@Data
public class CheckFriendVO {

    /**
     * 好友 ID
     */
    private Long friendId;

    /**
     * 检查状态
     */
    private Boolean checkStatus;

}
