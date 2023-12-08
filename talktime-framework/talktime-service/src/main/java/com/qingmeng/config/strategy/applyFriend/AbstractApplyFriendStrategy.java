package com.qingmeng.config.strategy.applyFriend;

import com.qingmeng.config.adapt.FriendAdapt;
import com.qingmeng.config.adapt.WsAdapter;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.dao.SysUserApplyDao;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.config.netty.service.WebSocketService;
import com.qingmeng.utils.AssertUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 抽象实现类
 * @createTime 2023年11月27日 14:18:00
 */
@Component
public abstract class AbstractApplyFriendStrategy implements ApplyFriendStrategy {
    @Resource
    private SysUserApplyDao sysUserApplyDao;
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private UserCache userCache;

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
     * 检查用户是否存在
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2023/11/28 17:06:23
     */
    protected void checkUserExist(Long userId){
        SysUser user = userCache.get(userId);
        AssertUtils.isNull(user,"用户不存在");
    }

    /**
     * 检查对方好友的权限
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 14:37:33
     */
    protected void checkAuthority(ApplyFriendDTO applyFriendDTO) {
    }

    /**
     * 获取  用户申请 信息
     *
     * @param applyFriendDTO dto
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/28 17:10:01
     */
    protected SysUserApply getSysUserApplyInfo(ApplyFriendDTO applyFriendDTO){
        return FriendAdapt.buildSaveSysUserApply(applyFriendDTO);
    }

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 14:18:08
     */
    @Override
    public void applyFriend(ApplyFriendDTO applyFriendDTO) {
        checkUserExist(applyFriendDTO.getTargetId());
        checkAuthority(applyFriendDTO);
        applyFriendDTO.setApplyChannel(createChannelInfo());
        SysUserApply sysUserApply = sysUserApplyDao.getApplyRecordByBothId(applyFriendDTO.getUserId(),applyFriendDTO.getTargetId());
        if (Objects.nonNull(sysUserApply)) {
            handlerApplyInfo(sysUserApply);
        } else {
            sysUserApplyDao.save(getSysUserApplyInfo(applyFriendDTO));
        }
        // websocket推送好友申请信息
        webSocketService.sendToUserId(WsAdapter.buildApplyInfoVO(), sysUserApply.getTargetId());
    }

    /**
     * 处理申请信息
     *
     * @param sysUserApply sys 用户申请
     * @author qingmeng
     * @createTime: 2023/11/27 17:17:51
     */
    private void handlerApplyInfo(SysUserApply sysUserApply) {
        Integer applyStatus = sysUserApply.getApplyStatus();
        AssertUtils.equal(applyStatus, ApplyStatusEnum.BLOCK.getCode(), "对方已拉黑，无法再次申请");
        AssertUtils.equal(applyStatus, ApplyStatusEnum.ACCEPT.getCode(), "对方已同意，请勿重复申请");
        if (Objects.equals(applyStatus, ApplyStatusEnum.APPLYING.getCode())) {
            sysUserApplyDao.unReadApplyRecord(sysUserApply.getId());
        } else if (Objects.equals(applyStatus, ApplyStatusEnum.REJECT.getCode())) {
            sysUserApplyDao.updateReapplyStatus(sysUserApply.getId());
        }
    }
}
