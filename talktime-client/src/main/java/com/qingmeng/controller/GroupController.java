package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.chatGroup.CreatGroupDTO;
import com.qingmeng.dto.chatGroup.InviteDTO;
import com.qingmeng.dto.chatGroup.KickOutDTO;
import com.qingmeng.service.GroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月06日 22:01:00
 */
@RestController
@RequestMapping("/group")
@SaCheckLogin
public class GroupController {
    @Resource
    private GroupService groupService;

    /**
     * 创建群聊
     *
     * @param creatGroupDTO 创建群聊 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/06 23:20:47
     */
    @PostMapping("/creatGroup")
    @SysLog(title = "群聊模块", content = "创建群聊")
    public CommonResult<String> creatGroup(@Valid @RequestBody CreatGroupDTO creatGroupDTO) {
        groupService.creatGroup(StpUtil.getLoginIdAsLong(), creatGroupDTO);
        return CommonResult.success("创建成功");
    }


    /**
     * 邀请
     *
     * @param inviteDTO 邀请 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/07 08:42:41
     */
    @PostMapping("/invite")
    @SysLog(title = "群聊模块", content = "邀请加入群聊")
    public CommonResult<String> invite(@Valid @RequestBody InviteDTO inviteDTO) {
        groupService.invite(StpUtil.getLoginIdAsLong(), inviteDTO);
        return CommonResult.success();
    }

    /**
     * 接受邀请
     *
     * @param groupRoomId 组会议室 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/08 08:33:28
     */
    @PostMapping("/acceptInvite/{groupRoomId}")
    @SysLog(title = "群聊模块", content = "接受邀请")
    public CommonResult<String> acceptInvite(@PathVariable Long groupRoomId) {
        groupService.acceptInvite(StpUtil.getLoginIdAsLong(), groupRoomId);
        return CommonResult.success("加入成功");
    }

    /**
     * 踢出
     *
     * @param kickOutDTO 踢出 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/08 09:22:49
     */
    @PostMapping("/kickOut")
    @SaCheckRole(value = {"GroupOwner","Management"},mode = SaMode.OR)
    @SysLog(title = "群聊模块", content = "踢出群聊")
    public CommonResult<String> kickOut(@Valid @RequestBody KickOutDTO kickOutDTO) {
        groupService.kickOut(kickOutDTO);
        return CommonResult.success();
    }

}
