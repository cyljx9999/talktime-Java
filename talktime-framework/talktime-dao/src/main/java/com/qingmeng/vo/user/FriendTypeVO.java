package com.qingmeng.vo.user;

import lombok.Data;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友类型VO
 * @createTime 2023年12月03日 10:40:00
 */
@Data
public class FriendTypeVO {

    /**
     * 类型
     */
    private String type;

    /**
     * 用户列表
     */
    private List<SimpleUserInfo> userList;

}
