package com.qingmeng.adapt;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.vo.chat.SimpleChatInfoVO;
import com.qingmeng.vo.user.ClickFriendInfoVo;
import com.qingmeng.vo.user.PersonalInfoVO;

import java.util.List;

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

    /**
     * 建立点击好友信息 vo
     *
     * @param sysUserFriendSetting SYS 用户好友设置
     * @param sysUser              sys 用户
     * @param togetherGroupList    一起组列表
     * @return {@link ClickFriendInfoVo }
     * @author qingmeng
     * @createTime: 2023/12/02 10:09:30
     */
    public static ClickFriendInfoVo buildClickFriendInfoVo(SysUserFriendSetting sysUserFriendSetting, SysUser sysUser, List<SimpleChatInfoVO> togetherGroupList){
        ClickFriendInfoVo vo = new ClickFriendInfoVo();
        String nickName = sysUserFriendSetting.getNickName();
        if (StrUtil.isNotBlank(nickName)){
            vo.setNickName(nickName);
            vo.setRemarkStatus(Boolean.TRUE);
        }else {
            vo.setRemarkStatus(Boolean.FALSE);
        }
        vo.setUserName(sysUser.getUserName());
        vo.setUserAvatar(sysUser.getUserAvatar());
        vo.setUserAccount(sysUser.getUserAccount());
        vo.setUserSex(sysUser.getUserSex());
        vo.setTogetherGroupList(togetherGroupList);
        vo.setAddChannel(sysUserFriendSetting.getAddChannel());
        return vo;
    }

}
