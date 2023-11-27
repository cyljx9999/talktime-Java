package com.qingmeng.adapt;

import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.enums.user.ReadStatusEnum;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友信息适配器
 * @createTime 2023年11月27日 14:26:00
 */
public class FriendAdapt {

    /**
     * 构建保存好友信息
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/27 14:31:48
     */
    public static SysUserApply buildSaveSysUserApply(ApplyFriendDTO applyFriendDTO){
        SysUserApply sysUserApply = new SysUserApply();
        sysUserApply.setUserId(applyFriendDTO.getUserId());
        sysUserApply.setApplyStatus(ApplyStatusEnum.APPLYING.getCode());
        sysUserApply.setTargetId(applyFriendDTO.getTargetId());
        sysUserApply.setApplyDescribe(applyFriendDTO.getApplyDescribe());
        sysUserApply.setApplyChannel(applyFriendDTO.getApplyChannel());
        sysUserApply.setReadStatus(ReadStatusEnum.UNREAD.getCode());
        return sysUserApply;
    }

}
