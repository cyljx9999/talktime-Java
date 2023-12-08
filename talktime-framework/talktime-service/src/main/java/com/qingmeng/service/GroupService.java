package com.qingmeng.service;

import com.qingmeng.dto.chatGroup.*;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月06日 22:03:00
 */
public interface GroupService {
    /**
     * 创建群聊
     *
     * @param userId        用户 ID
     * @param creatGroupDTO 创建群聊 DTO
     * @author qingmeng
     * @createTime: 2023/12/06 22:07:59
     */
    void creatGroup(Long userId, CreatGroupDTO creatGroupDTO);

    /**
     * 邀请
     *
     * @param userId    用户 ID
     * @param inviteDTO 邀请 DTO
     * @author qingmeng
     * @createTime: 2023/12/07 08:42:51
     */
    void invite(Long userId, InviteDTO inviteDTO);

    /**
     * 接受邀请
     *
     * @param userId      用户 ID
     * @param groupRoomId 组会议室 ID
     * @author qingmeng
     * @createTime: 2023/12/08 08:33:39
     */
    void acceptInvite(Long userId, Long groupRoomId);

    /**
     * 踢出
     *
     * @param kickOutDTO 踢出 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 09:26:28
     */
    void kickOut(KickOutDTO kickOutDTO);

    /**
     * 更改设置
     *
     * @param alterGroupSettingDTO 更改组设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 10:32:12
     */
    void alterSetting(AlterGroupSettingDTO alterGroupSettingDTO);

    /**
     * 添加管理
     *
     * @param addManagementDTO 添加管理 DTO
     * @author qingmeng
     * @createTime: 2023/12/08 10:39:57
     */
    void addManagement(AddManagementDTO addManagementDTO);
}
