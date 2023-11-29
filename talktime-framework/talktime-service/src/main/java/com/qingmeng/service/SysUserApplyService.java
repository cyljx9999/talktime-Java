package com.qingmeng.service;

import com.qingmeng.dto.common.PageDTO;
import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.vo.common.CommonPageVO;
import com.qingmeng.vo.user.FriendApplyRecordVO;

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
     * @param userId  用户 ID
     * @param pageDTO 分页 dto
     * @return {@link CommonPageVO }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:06:05
     */
    CommonPageVO<FriendApplyRecordVO> getFriendApplyListByUserId(Long userId, PageDTO pageDTO);

    /**
     * 根据id获取未读申请记录计数
     *
     * @param userId 用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2023/11/29 09:12:30
     */
    Long getUnReadApplyRecordCountByUserId(Long userId);
}
