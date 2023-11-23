package com.qingmeng.adapt;

import com.qingmeng.entity.SysUser;
import com.qingmeng.vo.user.PersonalInfoVO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户信息相关适配器
 * @createTime 2023年11月23日 22:02:00
 */
public class UserInfoAdapt {

    /**
     * 建立个人信息 vo
     *
     * @param sysUser sys 用户
     * @return {@link PersonalInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/23 22:04:27
     */
    public static PersonalInfoVO buildPersonalInfoVO(SysUser sysUser){
        PersonalInfoVO personalInfoVO = new PersonalInfoVO();
        personalInfoVO.setId(sysUser.getId());
        personalInfoVO.setUserName(sysUser.getUserName());
        personalInfoVO.setUserAvatar(sysUser.getUserAvatar());
        personalInfoVO.setUserAccount(sysUser.getUserAccount());
        personalInfoVO.setUserPhone(sysUser.getUserPhone());
        personalInfoVO.setUserSex(sysUser.getUserSex());
        personalInfoVO.setQrcodeUrl(sysUser.getQrcodeUrl());
        personalInfoVO.setAlterAccountCount(sysUser.getAlterAccountCount());
        return personalInfoVO;
    }

}
