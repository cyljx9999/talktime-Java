package com.qingmeng.strategy.applyFriend;

import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.cache.UserSettingCache;
import com.qingmeng.dao.SysUserApplyDao;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.enums.user.CloseOrOpenStatusEnum;
import com.qingmeng.utils.AsserUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 通过手机号添加
 * @createTime 2023年11月27日 14:33:00
 */
@Component
public class ApplyByPhoneStrategy extends AbstractApplyFriendStrategy{
    @Resource
    private SysUserApplyDao sysUserApplyDao;
    @Resource
    private UserSettingCache userSettingCache;


    /**
     * 生成获取渠道信息
     *
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/27 14:32:55
     */
    @Override
    protected String createChannelInfo() {
        return "对方通过 搜索手机号 添加";
    }

    /**
     * 检查
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 14:37:37
     */
    @Override
    protected void check(ApplyFriendDTO applyFriendDTO) {
        SysUserPrivacySetting setting = userSettingCache.get(applyFriendDTO.getUserId());
        AsserUtils.equal(setting.getAddByPhone(), CloseOrOpenStatusEnum.OPEN.getCode(),"对方开启隐私设置，无法添加");
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
        sysUserApplyDao.save(FriendAdapt.buildSaveSysUserApply(applyFriendDTO));
    }
}
