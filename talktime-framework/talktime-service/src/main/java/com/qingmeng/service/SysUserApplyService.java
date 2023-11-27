package com.qingmeng.service;

import com.qingmeng.dto.user.ApplyFriendDTO;

/**
 * <p>
 * 好友申请表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysUserApplyService{

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 15:56:45
     */
    void applyFriend(ApplyFriendDTO applyFriendDTO);
}
