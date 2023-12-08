package com.qingmeng.config.strategy.applyFriend;

import com.qingmeng.dto.user.ApplyFriendDTO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 申请好友通用接口
 * @createTime 2023年11月27日 11:17:00
 */
public interface ApplyFriendStrategy {

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 14:18:08
     */
    void applyFriend(ApplyFriendDTO applyFriendDTO);

}
