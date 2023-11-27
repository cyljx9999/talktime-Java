package com.qingmeng.strategy.applyFriend;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUserApply;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 抽象实现类
 * @createTime 2023年11月27日 14:18:00
 */
@Component
public abstract class AbstractApplyFriendStrategy implements ApplyFriendStrategy {
    /**
     * 生成获取渠道信息
     *
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/27 14:32:55
     */
    protected String createChannelInfo() {
        return null;
    }

    /**
     * 生成获取渠道信息
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/27 14:57:13
     */
    protected String createChannelInfo(ApplyFriendDTO applyFriendDTO) {
        return null;
    }

    /**
     * 检查
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 14:37:33
     */
    protected void check(ApplyFriendDTO applyFriendDTO) {}

    /**
     * 获取申请好友对象
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/27 14:32:33
     */
    protected SysUserApply getSysUserApply(ApplyFriendDTO applyFriendDTO){
        return FriendAdapt.buildSaveSysUserApply(applyFriendDTO);
    }
}
