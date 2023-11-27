package com.qingmeng.service.impl;

import com.qingmeng.dao.SysUserApplyDao;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.enums.user.ApplyFriendChannelEnum;
import com.qingmeng.service.SysUserApplyService;
import com.qingmeng.strategy.applyFriend.ApplyFriendFactory;
import com.qingmeng.strategy.applyFriend.ApplyFriendStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:18:00
 */
@Service
public class SysUserApplyServiceImpl implements SysUserApplyService {
    @Resource
    private SysUserApplyDao sysUserApplyDao;
    @Resource
    private ApplyFriendFactory applyFriendFactory;

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 15:56:45
     */
    @Override
    public void applyFriend(ApplyFriendDTO applyFriendDTO) {
        ApplyFriendChannelEnum channelEnum = ApplyFriendChannelEnum.get(applyFriendDTO.getApplyChannel());
        ApplyFriendStrategy strategyWithType = applyFriendFactory.getStrategyWithType(channelEnum.getValue());
        strategyWithType.applyFriend(applyFriendDTO);
    }
}
