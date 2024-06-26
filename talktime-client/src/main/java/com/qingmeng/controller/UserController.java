package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.config.annotation.Decrypt;
import com.qingmeng.config.annotation.Encrypt;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.dto.login.CheckFriendDTO;
import com.qingmeng.dto.login.CheckFriendListDTO;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.dto.user.AlterPersonalInfoDTO;
import com.qingmeng.dto.user.PersonalPrivacySettingDTO;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.service.SysUserService;
import com.qingmeng.vo.user.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户controller
 * @createTime 2023年11月11日 00:46:00
 */
@RestController
@RequestMapping("/user")
@SaCheckLogin
public class UserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserFriendService sysUserFriendService;


    /**
     * 检查登录
     *
     * @return {@link CommonResult }<{@link CheckLoginVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 09:35:01
     */
    @PostMapping("/checkLogin")
    @SysLog(title = "用户模块",content = "检测登陆")
    public CommonResult<CheckLoginVO> checkLogin(){
        CheckLoginVO checkLoginVO = sysUserService.checkLogin();
        return CommonResult.success(checkLoginVO);
    }


    /**
     * 获取用户信息
     *
     * @return {@link CommonResult }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/23 21:35:42
     */
    @GetMapping("/getPersonalInfo")
    @SysLog(title = "用户模块",content = "获取个人信息")
    @Encrypt
    public CommonResult<PersonalInfoVO> getPersonalInfo(){
        PersonalInfoVO personInfo = sysUserService.getPersonalInfo(StpUtil.getLoginIdAsLong());
        return CommonResult.success(personInfo);
    }

    /**+
     * 更改账户
     *
     * @param alterAccountDTO 更改帐户 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/23 21:44:24
     */
    @PutMapping("/alterAccount")
    @SysLog(title = "用户模块",content = "修改账号")
    public CommonResult<String> alterAccount(@Valid @RequestBody AlterAccountDTO alterAccountDTO){
        sysUserService.alterAccount(StpUtil.getLoginIdAsLong(),alterAccountDTO);
        return CommonResult.success("修改成功");
    }

    /**+
     * 更改个人信息
     *
     * @param alterAccountPersonalInfoDTO 更改帐户 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/23 21:44:24
     */
    @PutMapping("/alterPersonalInfo")
    @SysLog(title = "用户模块",content = "更改个人信息")
    @Decrypt(value = AlterPersonalInfoDTO.class)
    public CommonResult<String> alterPersonalInfo(@RequestBody AlterPersonalInfoDTO alterAccountPersonalInfoDTO){
        sysUserService.alterPersonalInfo(StpUtil.getLoginIdAsLong(),alterAccountPersonalInfoDTO);
        return CommonResult.success("修改成功");
    }

    /**
     * 获取个人隐私设置
     *
     * @return {@link CommonResult }<{@link PersonalInfoVO }>
     * @author qingmeng
     * @createTime: 2023/12/02 10:46:56
     */
    @GetMapping("/getPersonalPrivacySetting")
    @SysLog(title = "用户模块",content = "获取个人隐私设置")
    public CommonResult<PersonalPrivacySettingVO> getPersonalPrivacySetting(){
        PersonalPrivacySettingVO privacySetting = sysUserService.getPersonalPrivacySetting(StpUtil.getLoginIdAsLong());
        return CommonResult.success(privacySetting);
    }


    /**
     * 修改个人隐私设置
     *
     * @return {@link CommonResult }<{@link PersonalInfoVO }>
     * @author qingmeng
     * @createTime: 2023/12/02 10:46:56
     */
    @PutMapping("/alterPersonalPrivacySetting")
    @SysLog(title = "用户模块",content = "修改个人隐私设置")
    public CommonResult<String> alterPersonalPrivacySetting(@Valid @RequestBody PersonalPrivacySettingDTO personalPrivacySettingDTO){
        sysUserService.alterPersonalPrivacySetting(StpUtil.getLoginIdAsLong(),personalPrivacySettingDTO);
        return CommonResult.success("修改成功");
    }



    /**
     * 通过好友 ID 获取设置
     *
     * @param friendId 好友ID
     * @return {@link CommonResult }<{@link UserFriendSettingVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 14:41:18
     */
    @GetMapping("/getFriendSetting/{friendId}")
    @SysLog(title = "用户模块",content = "获取用户好友设置信息")
    public CommonResult<UserFriendSettingVO> getFriendSetting(@PathVariable Long friendId){
        UserFriendSettingVO settingVO = sysUserFriendService.getFriendSettingByBothId(StpUtil.getLoginIdAsLong(),friendId);
        return CommonResult.success(settingVO);
    }

    /**
     * 更改设置
     *
     * @param userFriendSettingDTO 用户好友设置 DTO
     * @return {@link CommonResult }<{@link UserFriendSettingVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 15:14:51
     */
    @PutMapping("/alterFriendSetting")
    @SysLog(title = "用户模块",content = "修改用户好友设置信息")
    public CommonResult<UserFriendSettingVO> alterFriendSetting(@Valid @RequestBody UserFriendSettingDTO userFriendSettingDTO){
        sysUserFriendService.alterFriendSetting(StpUtil.getLoginIdAsLong(),userFriendSettingDTO);
        return CommonResult.success("修改成功");
    }

    /**
     * 删除好友
     *
     * @param friendId 好友 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/01 09:01:34
     */
    @DeleteMapping("/deleteFriend/{friendId}")
    @SysLog(title = "用户模块",content = "删除好友")
    public CommonResult<String> deleteFriend(@PathVariable Long friendId){
        sysUserService.deleteFriend(StpUtil.getLoginIdAsLong(),friendId);
        return CommonResult.success("删除成功");
    }

    /**
     * 点击获取好友信息
     *
     * @param friendId 好友ID
     * @return {@link CommonResult }<{@link ClickFriendInfoVo }>
     * @author qingmeng
     * @createTime: 2023/12/02 09:51:51
     */
    @GetMapping("/getFriendInfoByClick/{friendId}")
    @SysLog(title = "用户模块",content = "点击查看好友信息")
    public CommonResult<ClickFriendInfoVo> getFriendInfoByClick(@PathVariable Long friendId){
        ClickFriendInfoVo clickFriendInfoVo =  sysUserService.getFriendInfoByClick(StpUtil.getLoginIdAsLong(),friendId);
        return CommonResult.success(clickFriendInfoVo);
    }


    /**
     * 获取好友列表
     *
     * @return {@link CommonResult }<{@link List }<{@link FriendTypeVO }>>
     * @author qingmeng
     * @createTime: 2023/12/03 10:43:21
     */
    @GetMapping("/getFriendList")
    @SysLog(title = "用户模块",content = "获取好友列表")
    public CommonResult<List<FriendTypeVO>> getFriendList(){
        List<FriendTypeVO> list = sysUserFriendService.getFriendList(StpUtil.getLoginIdAsLong());
        return CommonResult.success(list);
    }

    /**
     * 检查是否为好友
     *
     * @param checkFriendDTO 检查好友 DTO
     * @return {@link CommonResult }<{@link CheckFriendVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 13:09:17
     */
    @PostMapping("/checkFriend")
    @SysLog(title = "用户模块",content = "检查是否为好友")
    public CommonResult<CheckFriendVO> checkFriend(@Valid @RequestBody CheckFriendDTO checkFriendDTO){
        CheckFriendVO checkFriendVO = sysUserFriendService.checkFriend(StpUtil.getLoginIdAsLong(),checkFriendDTO);
        return CommonResult.success(checkFriendVO);
    }

    /**
     * 批量检查是否为好友
     *
     * @param checkFriendListDTO 检查好友 DTO
     * @return {@link CommonResult }<{@link List }<{@link CheckFriendVO }>>
     * @author qingmeng
     * @createTime: 2023/12/03 12:49:01
     */
    @PostMapping("/checkFriendList")
    @SysLog(title = "用户模块",content = "批量检查是否为好友")
    public CommonResult<List<CheckFriendVO>> checkFriendList(@Valid @RequestBody CheckFriendListDTO checkFriendListDTO){
        List<CheckFriendVO> list = sysUserFriendService.checkFriendList(StpUtil.getLoginIdAsLong(),checkFriendListDTO);
        return CommonResult.success(list);
    }
}
