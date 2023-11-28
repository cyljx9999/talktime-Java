package com.qingmeng.service;

import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.vo.user.FriendApplyRecordVO;

import java.util.List;

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

    /**
     * 同意申请好友
     *
     * @param agreeApplyFriendDTO 同意申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/28 17:30:48
     */
    void agreeApply(AgreeApplyFriendDTO agreeApplyFriendDTO);

    /**
     * 根据userId获取好友申请列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/28 23:21:45
     */
    List<FriendApplyRecordVO> getFriendApplyListByUserId(Long userId);
}
