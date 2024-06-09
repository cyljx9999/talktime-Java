package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.dto.chatGroup.*;
import com.qingmeng.service.GroupService;
import com.qingmeng.vo.chat.GroupDetailInfoVO;
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
    @SaCheckRole(value = {"GroupOwner", "Management"}, mode = SaMode.OR)
    @SysLog(title = "群聊模块", content = "踢出群聊")
    public CommonResult<String> kickOut(@Valid @RequestBody KickOutDTO kickOutDTO) {
        groupService.kickOut(StpUtil.getLoginIdAsLong(),kickOutDTO);
        return CommonResult.success();
    }


    /**
     * 更改设置
     *
     * @param alterGroupSettingDTO 更改组设置 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/08 10:32:06
     */
    @PutMapping("/alterSetting")
    @SaCheckRole(value = {"GroupOwner", "Management"}, mode = SaMode.OR)
    @SysLog(title = "群聊模块", content = "修改群聊设置")
    public CommonResult<String> alterSetting(@Valid @RequestBody AlterGroupSettingDTO alterGroupSettingDTO) {
        groupService.alterSetting(alterGroupSettingDTO);
        return CommonResult.success();
    }


    /**
     * 添加管理
     *
     * @param addManagementDTO 添加管理 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/08 10:39:52
     */
    @PostMapping("/addManagement")
    @SaCheckRole("GroupOwner")
    @SysLog(title = "群聊模块", content = "添加管理员")
    public CommonResult<String> addManagement(@Valid @RequestBody AddManagementDTO addManagementDTO) {
        groupService.addManagement(addManagementDTO);
        return CommonResult.success();
    }

    /**
     * 删除管理
     *
     * @param removeManagementDTO 移除管理 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/09 13:30:05
     */
    @PostMapping("/removeManagement")
    @SaCheckRole("GroupOwner")
    @SysLog(title = "群聊模块", content = "移除管理员")
    public CommonResult<String> removeManagement(@Valid @RequestBody RemoveManagementDTO removeManagementDTO) {
        groupService.removeManagement(removeManagementDTO);
        return CommonResult.success();
    }

    /**
     * 修改个人设置
     *
     * @param alterGroupPersonalSettingDTO 修改个人设置 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/09 13:39:11
     */
    @PutMapping("/alterPersonSetting")
    @SysLog(title = "群聊模块", content = "修改个人群聊设置")
    public CommonResult<String> alterPersonSetting(@Valid @RequestBody AlterGroupPersonalSettingDTO alterGroupPersonalSettingDTO) {
        groupService.alterPersonSetting(StpUtil.getLoginIdAsLong(), alterGroupPersonalSettingDTO);
        return CommonResult.success();
    }

    /**
     * 获取组详细信息
     *
     * @param roomId 房间 ID
     * @return {@link CommonResult }<{@link GroupDetailInfoVO }>
     * @author qingmeng
     * @createTime: 2023/12/09 13:59:09
     */
    @GetMapping("/getGroupDetailInfo/{roomId}")
    @SysLog(title = "群聊模块", content = "获取群聊详细信息")
    public CommonResult<GroupDetailInfoVO> getGroupDetailInfo(@PathVariable Long roomId) {
        GroupDetailInfoVO groupDetailInfo = groupService.getGroupDetailInfo(StpUtil.getLoginIdAsLong(), roomId);
        return CommonResult.success(groupDetailInfo);
    }

    /**
     * 退出聊天群
     *
     * @param groupRoomId 组会议室 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/09 14:58:05
     */
    @PostMapping("/quitChatGroup/{groupRoomId}")
    @SysLog(title = "群聊模块", content = "退出群聊")
    public CommonResult<String> quitChatGroup(@PathVariable Long groupRoomId) {
        groupService.quitChatGroup(StpUtil.getLoginIdAsLong(), groupRoomId);
        return CommonResult.success();
    }

}
