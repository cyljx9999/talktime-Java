package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.service.SysUserApplyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户申请好友控制层
 * @createTime 2023年11月27日 15:51:00
 */
@RestController
@RequestMapping("/apply")
@SaCheckLogin
public class UserAllpyFriendController {
    @Resource
    private SysUserApplyService sysUserApplyService;

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/27 15:56:31
     */
    @PostMapping("/add")
    public CommonResult<String> applyFriend(@Valid @RequestBody ApplyFriendDTO applyFriendDTO){
        applyFriendDTO.setUserId(StpUtil.getLoginIdAsLong());
        sysUserApplyService.applyFriend(applyFriendDTO);
        return CommonResult.success("申请成功");
    }

}
