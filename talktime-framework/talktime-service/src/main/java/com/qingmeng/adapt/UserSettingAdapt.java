package com.qingmeng.adapt;

import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.enums.chat.MessageTopStatusEnum;
import com.qingmeng.enums.user.CloseOrOpenStatusEnum;
import com.qingmeng.enums.user.FriendStausEnum;
import com.qingmeng.vo.user.UserFriendSettingVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户设置
 * @createTime 2023年11月27日 10:59:00
 */
public class UserSettingAdapt {

    /**
     * 构建 默认 用户隐私设置 对象
     *
     * @param userId 用户 ID
     * @return {@link SysUserPrivacySetting }
     * @author qingmeng
     * @createTime: 2023/11/27 11:00:11
     */
    public static SysUserPrivacySetting buildDefalutSysUserPrivacySetting(Long userId) {
        SysUserPrivacySetting setting = new SysUserPrivacySetting();
        setting.setUserId(userId);
        setting.setAddByAccount(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByPhone(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByGroup(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByCard(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByQrcode(CloseOrOpenStatusEnum.OPEN.getCode());
        return setting;
    }

    /**
     * 构建 默认 用户好友设置
     *
     * @param ids          IDS
     * @param tagKey       标签键
     * @param applyChannel 应用渠道
     * @return {@link List }<{@link SysUserFriendSetting }>
     * @author qingmeng
     * @createTime: 2023/11/29 14:51:23
     */
    public static List<SysUserFriendSetting> buildDefaultSysUserFriendSetting(List<Long> ids,String tagKey,String applyChannel) {
        return ids.stream().map(id -> {
            SysUserFriendSetting sysUserFriendSetting = new SysUserFriendSetting();
            sysUserFriendSetting.setTagKey(tagKey);
            sysUserFriendSetting.setUserId(id);
            sysUserFriendSetting.setFriendStatus(FriendStausEnum.NORMAL.getCode());
            sysUserFriendSetting.setTopStatus(MessageTopStatusEnum.NORMAL.getCode());
            sysUserFriendSetting.setAddChannel(applyChannel);
            return sysUserFriendSetting;
        }).collect(Collectors.toList());
    }


    /**
     * 构建 用户好友设置 VO
     *
     * @param sysUserFriendSetting SYS 用户好友设置
     * @return {@link UserFriendSettingVO }
     * @author qingmeng
     * @createTime: 2023/11/29 14:48:01
     */
    public static UserFriendSettingVO buildUserFriendSettingVO(SysUserFriendSetting sysUserFriendSetting) {
        UserFriendSettingVO vo = new UserFriendSettingVO();
        vo.setSettingId(sysUserFriendSetting.getId());
        vo.setNickName(sysUserFriendSetting.getNickName());
        vo.setFriendStatus(sysUserFriendSetting.getFriendStatus());
        vo.setTopStatus(sysUserFriendSetting.getTopStatus());
        vo.setAddChannel(sysUserFriendSetting.getAddChannel());
        vo.setCreateTime(sysUserFriendSetting.getCreateTime());
        return vo;
    }


}
