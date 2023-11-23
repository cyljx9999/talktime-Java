package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.service.SysUserService;
import com.qingmeng.vo.user.PersonalInfoVO;
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

    /**
     * 获取用户信息
     *
     * @return {@link CommonResult }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/23 21:35:42
     */
    @GetMapping("/getPersonalInfo")
    public CommonResult<PersonalInfoVO> getPersonalInfo(){
        PersonalInfoVO personInfo = sysUserService.getPersonalInfo(StpUtil.getLoginIdAsLong());
        return CommonResult.success(personInfo);
    }

    /**
     * 更改帐户
     *
     * @param alterAccountDTO 更改帐户 DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/23 21:44:24
     */
    @PutMapping("/alterAccount")
    public CommonResult<String> alterAccount(@Valid @RequestBody AlterAccountDTO alterAccountDTO){
        sysUserService.alterAccount(StpUtil.getLoginIdAsLong(),alterAccountDTO);
        return CommonResult.success("修改成功");
    }

}
