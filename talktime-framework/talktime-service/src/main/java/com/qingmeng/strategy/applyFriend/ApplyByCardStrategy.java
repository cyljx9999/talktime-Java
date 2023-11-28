package com.qingmeng.strategy.applyFriend;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.adapt.FriendAdapt;
import com.qingmeng.cache.UserCache;
import com.qingmeng.cache.UserFriendSettingCache;
import com.qingmeng.cache.UserSettingCache;
import com.qingmeng.dao.SysUserApplyDao;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.enums.user.CloseOrOpenStatusEnum;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.utils.CommonUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 通过分享名字添加
 * @createTime 2023年11月27日 14:33:00
 */
@Component
public class ApplyByCardStrategy extends AbstractApplyFriendStrategy{
    @Resource
    private SysUserApplyDao sysUserApplyDao;
    @Resource
    private UserSettingCache userSettingCache;
    @Resource
    private UserCache userCache;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;


    /**
     * 生成获取渠道信息
     *
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/27 14:32:55
     */
    @Override
    protected String createChannelInfo(ApplyFriendDTO applyFriendDTO) {
        SysUser sysUser = userCache.get(applyFriendDTO.getShareCardByUserId());
        List<Long> ids = Arrays.asList(applyFriendDTO.getUserId(), applyFriendDTO.getShareCardByUserId());
        SysUserFriendSetting sysUserFriendSetting = userFriendSettingCache.get(CommonUtils.getKeyBySort(ids));
        String nickName = sysUserFriendSetting.getNickName();
        String name = StrUtil.isNotBlank(nickName) ? nickName : sysUser.getUserName();
        return "对方通过 " + name + " 分享的名片添加";
    }

    /**
     * 检查
     *
     * @param applyFriendDTO 申请好友 dto
     * @author qingmeng
     * @createTime: 2023/11/27 14:37:37
     */
    @Override
    protected void checkAuthority(ApplyFriendDTO applyFriendDTO) {
        SysUserPrivacySetting setting = userSettingCache.get(applyFriendDTO.getUserId());
        AsserUtils.equal(setting.getAddByCard(), CloseOrOpenStatusEnum.OPEN.getCode(),"对方开启隐私设置，无法添加");
        AsserUtils.isNotNull(applyFriendDTO.getShareCardByUserId(),"缺少分享者的用户id");
    }

    /**
     * 获取  用户申请 信息
     *
     * @param applyFriendDTO dto
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/28 17:10:01
     */
    @Override
    protected SysUserApply getSysUserApplyInfo(ApplyFriendDTO applyFriendDTO){
        applyFriendDTO.setApplyChannel(createChannelInfo(applyFriendDTO));
        return FriendAdapt.buildSaveSysUserApply(applyFriendDTO);
    }
}
