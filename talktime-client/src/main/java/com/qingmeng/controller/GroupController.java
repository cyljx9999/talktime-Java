package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.group.CreatGroupDTO;
import com.qingmeng.service.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/creatGroup")
    public CommonResult<String> creatGroup(@Valid @RequestBody CreatGroupDTO creatGroupDTO){
        groupService.creatGroup(StpUtil.getLoginIdAsLong(),creatGroupDTO);
        return CommonResult.success("创建成功");
    }


}
