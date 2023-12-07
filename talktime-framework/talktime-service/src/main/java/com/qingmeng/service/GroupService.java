package com.qingmeng.service;

import com.qingmeng.dto.chatGroup.CreatGroupDTO;
import com.qingmeng.dto.chatGroup.InviteDTO;

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
}
