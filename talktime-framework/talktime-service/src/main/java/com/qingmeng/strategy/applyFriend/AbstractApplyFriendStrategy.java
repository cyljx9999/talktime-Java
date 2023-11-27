package com.qingmeng.strategy.applyFriend;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.dao.SysUserApplyDao;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.enums.user.ReadStatusEnum;
import com.qingmeng.utils.AsserUtils;
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
    protected void check(ApplyFriendDTO applyFriendDTO) {
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
        check(applyFriendDTO);
        applyFriendDTO.setApplyChannel(createChannelInfo());
        SysUserApply sysUserApply = sysUserApplyDao.lambdaQuery()
                .eq(SysUserApply::getUserId, applyFriendDTO.getUserId())
                .eq(SysUserApply::getTargetId, applyFriendDTO.getTargetId())
                .one();
        if (Objects.nonNull(sysUserApply)) {
            handlerApplyInfo(sysUserApply);
        } else {
            sysUserApplyDao.save(FriendAdapt.buildSaveSysUserApply(applyFriendDTO));
        }
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
        AsserUtils.equal(applyStatus, ApplyStatusEnum.BLOCK.getCode(), "对方已拉黑，无法再次申请");
        AsserUtils.equal(applyStatus, ApplyStatusEnum.ACCEPT.getCode(), "对方已同意，请勿重复申请");
        if (Objects.equals(applyStatus, ApplyStatusEnum.APPLYING.getCode())) {
            sysUserApply.setReadStatus(ReadStatusEnum.READ.getCode());
            sysUserApplyDao.updateById(sysUserApply);
            // todo websocket推送好友申请信息
        }else if (Objects.equals(applyStatus, ApplyStatusEnum.REJECT.getCode())) {
            sysUserApply.setApplyStatus(ApplyStatusEnum.APPLYING.getCode());
            sysUserApply.setReadStatus(ReadStatusEnum.READ.getCode());
            sysUserApplyDao.updateById(sysUserApply);
            // todo websocket推送好友申请信息
        }
    }
}
