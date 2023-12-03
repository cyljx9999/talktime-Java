package com.qingmeng.vo.user;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户简单信息
 * @createTime 2023年12月03日 10:40:00
 */
@Data
public class SimpleUserInfo {

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户名(如果给好友设置了备注名字则这里表示备注信息，否则表示用户名)
     */
    private String userName;

}
