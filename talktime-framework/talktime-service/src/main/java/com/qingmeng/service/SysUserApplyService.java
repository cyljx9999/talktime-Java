package com.qingmeng.service;

import com.qingmeng.dto.common.PageDTO;
import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.vo.common.CommonPageVO;
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

    /**
     * 拉黑申请记录
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 10:33:45
     */
    void blockApplyRecord(Long applyId);

    /**
     * 获取拉黑申请记录列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 10:50:08
     */
    List<FriendApplyRecordVO> getBlockApplyListByUserId(Long userId);

    /**
     * 按用户 ID 删除申请记录
     *
     * @param userId    用户id
     * @param applyId 申请 ID
     * @author qingmeng
     * @createTime: 2023/11/29 11:08:01
     */
    void deleteApplyRecordByUserId(Long userId, Long applyId);

    /**
     * 取消拉黑申请记录
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 11:24:22
     */
    void cancelBlockApplyRecord(Long applyId);
}
