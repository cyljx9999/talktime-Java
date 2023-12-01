package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.service.SysUserService;
import com.qingmeng.vo.user.PersonalInfoVO;
import com.qingmeng.vo.user.UserFriendSettingVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
     * 获取用户信息
     *
     * @return {@link CommonResult }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/23 21:35:42
     */
    @GetMapping("/getPersonalInfo")
    @SysLog(title = "用户模块",content = "获取个人信息")
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

    /**
     * 通过两个 ID 获取设置
     *
     * @param friendId 好友ID
     * @return {@link CommonResult }<{@link UserFriendSettingVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 14:41:18
     */
    @GetMapping("/getFriendSetting/{friendId}")
    @SysLog(title = "用户模块",content = "获取用户好友设置信息")
    public CommonResult<UserFriendSettingVO> getFriendSettingByBothId(@PathVariable Long friendId){
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

}
