package com.qingmeng.adapt;

import com.qingmeng.entity.SysUserPrivacySetting;
import com.qingmeng.enums.user.CloseOrOpenStatusEnum;

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
    public static SysUserPrivacySetting buildDefalutSysUserPrivacySetting(Long userId){
        SysUserPrivacySetting setting = new SysUserPrivacySetting();
        setting.setUserId(userId);
        setting.setAddByAccount(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByPhone(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByGroup(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByCard(CloseOrOpenStatusEnum.OPEN.getCode());
        setting.setAddByQrcode(CloseOrOpenStatusEnum.OPEN.getCode());
        return setting;
    }

}
