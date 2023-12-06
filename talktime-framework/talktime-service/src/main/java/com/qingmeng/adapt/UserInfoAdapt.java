package com.qingmeng.adapt;

import cn.hutool.core.util.StrUtil;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.vo.chat.SimpleChatInfoVO;
import com.qingmeng.vo.common.ScanQrcodeInfoVO;
import com.qingmeng.vo.user.CheckLoginVO;
import com.qingmeng.vo.user.ClickFriendInfoVo;
import com.qingmeng.vo.user.PersonalInfoVO;
import com.qingmeng.vo.user.UserPartialInfoVO;

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
        vo.setLocation(sysUser.getIpLocation());
        return vo;
    }

    /**
     * 构建检查登录 vo
     *
     * @param status 地位
     * @return {@link CheckLoginVO }
     * @author qingmeng
     * @createTime: 2023/12/03 09:36:20
     */
    public static CheckLoginVO buildCheckLoginVO(boolean status) {
        CheckLoginVO loginVO = new CheckLoginVO();
        loginVO.setLoginStatus(status);
        return loginVO;
    }

    /**
     * 扫描二维码信息给朋友VO
     *
     * @param friendSetting     好友设置
     * @param sysUser           sys 用户
     * @param togetherGroupList 一起组列表
     * @return {@link ScanQrcodeInfoVO }<{@link ? }>
     * @author qingmeng
     * @createTime: 2023/12/06 11:33:40
     */
    public static ScanQrcodeInfoVO<?> scanQrcodeInfoToFriendVO(SysUserFriendSetting friendSetting, SysUser sysUser, List<SimpleChatInfoVO> togetherGroupList) {
        ClickFriendInfoVo clickFriendInfoVo = buildClickFriendInfoVo(friendSetting, sysUser, togetherGroupList);
        ScanQrcodeInfoVO<ClickFriendInfoVo> scanQrcodeInfoVO = new ScanQrcodeInfoVO<>();
        scanQrcodeInfoVO.setDataInfo(clickFriendInfoVo);
        return scanQrcodeInfoVO;
    }

    /**
     * 扫描二维码信息到用户部分VO
     *
     * @param sysUser sys 用户
     * @return {@link ScanQrcodeInfoVO }<{@link ? }>
     * @author qingmeng
     * @createTime: 2023/12/06 11:38:20
     */
    public static ScanQrcodeInfoVO<?> scanQrcodeInfoToUserPartialVO(SysUser sysUser) {
        UserPartialInfoVO userPartialInfoVO = buildUserPartialInfoVO(sysUser);
        ScanQrcodeInfoVO<UserPartialInfoVO> scanQrcodeInfoVO = new ScanQrcodeInfoVO<>();
        scanQrcodeInfoVO.setDataInfo(userPartialInfoVO);
        return scanQrcodeInfoVO;
    }

    /**
     * 构建用户部分信息 vo
     *
     * @param sysUser sys 用户
     * @return {@link UserPartialInfoVO }
     * @author qingmeng
     * @createTime: 2023/12/06 11:37:49
     */
    public static UserPartialInfoVO buildUserPartialInfoVO(SysUser sysUser){
        UserPartialInfoVO userPartialInfoVO = new UserPartialInfoVO();
        userPartialInfoVO.setUserName(sysUser.getUserName());
        userPartialInfoVO.setUserAvatar(sysUser.getUserAvatar());
        userPartialInfoVO.setUserAccount(sysUser.getUserAccount());
        userPartialInfoVO.setUserSex(sysUser.getUserSex());
        userPartialInfoVO.setLocation(sysUser.getIpLocation());
        return userPartialInfoVO;
    }
}
