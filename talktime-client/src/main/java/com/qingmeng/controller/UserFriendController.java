package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.user.UserFriendSettingDTO;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.vo.user.UserFriendSettingVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友相关controller
 * @createTime 2023年11月29日 14:27:00
 */
@RestController
@RequestMapping("/friend")
@SaCheckLogin
public class UserFriendController {

    @Resource
    private SysUserFriendService sysUserFriendService;

    /**
     * 通过两个 ID 获取设置
     *
     * @param friendId 好友ID
     * @return {@link CommonResult }<{@link UserFriendSettingVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 14:41:18
     */
    @GetMapping("/getSetting/{friendId}")
    public CommonResult<UserFriendSettingVO> getSettingByBothId(@PathVariable Long friendId){
        UserFriendSettingVO settingVO = sysUserFriendService.getSettingByBothId(StpUtil.getLoginIdAsLong(),friendId);
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
    @PutMapping("/alterSetting")
    public CommonResult<UserFriendSettingVO> alterSetting(@RequestBody UserFriendSettingDTO userFriendSettingDTO){
        sysUserFriendService.alterSetting(StpUtil.getLoginIdAsLong(),userFriendSettingDTO);
        return CommonResult.success("修改成功");
    }

}
