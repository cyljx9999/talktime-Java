package com.qingmeng.service;

import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.vo.user.UserFriendSettingVO;

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
    UserFriendSettingVO getSettingByBothId(Long userId, Long friendId);

    /**
     * 更改设置
     *
     * @param userId               用户 ID
     * @param userFriendSettingDTO 用户好友设置 DTO
     * @author qingmeng
     * @createTime: 2023/11/29 15:15:19
     */
    void alterSetting(Long userId, UserFriendSettingDTO userFriendSettingDTO);
}
