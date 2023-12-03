package com.qingmeng.service;

import com.qingmeng.dto.login.CheckFriendDTO;
import com.qingmeng.dto.login.CheckFriendListDTO;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.vo.user.CheckFriendVO;
import com.qingmeng.vo.user.FriendTypeVO;
import com.qingmeng.vo.user.UserFriendSettingVO;

import java.util.List;

/**
 * <p>
 * 用户好友表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysUserFriendService {

    /**
     * 通过两个 ID 获取设置
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link UserFriendSettingVO }
     * @author qingmeng
     * @createTime: 2023/11/29 14:41:26
     */
    UserFriendSettingVO getFriendSettingByBothId(Long userId, Long friendId);

    /**
     * 更改设置
     *
     * @param userId               用户 ID
     * @param userFriendSettingDTO 用户好友设置 DTO
     * @author qingmeng
     * @createTime: 2023/11/29 15:15:19
     */
    void alterFriendSetting(Long userId, UserFriendSettingDTO userFriendSettingDTO);

    /**
     * 保存好友记录
     *
     * @param sysUserApply sys 用户申请
     * @author qingmeng
     * @createTime: 2023/12/01 16:51:18
     */
    void saveFriendRecord(SysUserApply sysUserApply);

    /**
     * 通过两个ID获取好友
     *
     * @param userId   用户 ID
     * @param targetId 目标 ID
     * @return {@link SysUserFriend }
     * @author qingmeng
     * @createTime: 2023/12/01 16:52:47
     */
    SysUserFriend getFriendByBothId(Long userId, Long targetId);

    /**
     * 获取好友列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link FriendTypeVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 11:02:52
     */
    List<FriendTypeVO> getFriendList(Long userId);

    /**
     * 检查是否为好友
     *
     * @param userId         用户 ID
     * @param checkFriendDTO 检查好友 DTO
     * @return {@link CheckFriendVO }
     * @author qingmeng
     * @createTime: 2023/12/03 13:09:02
     */
    CheckFriendVO checkFriend(Long userId,  CheckFriendDTO checkFriendDTO);


    /**
     * 批量检查是否为好友
     *
     * @param userId         用户 ID
     * @param checkFriendListDTO 检查好友 DTO
     * @return {@link List }<{@link CheckFriendVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 13:04:21
     */
    List<CheckFriendVO> checkFriendList(Long userId, CheckFriendListDTO checkFriendListDTO);

}
