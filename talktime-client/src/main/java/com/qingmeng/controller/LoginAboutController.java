package com.qingmeng.controller;

import com.qingmeng.domain.dto.login.LoginParamDTO;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.domain.vo.login.TokenInfo;
import com.qingmeng.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆香菇按controller
 * @createTime 2023年11月11日 00:47:00
 */
@RestController
@RequestMapping("/login")
public class LoginAboutController {
    @Resource
    private SysUserService sysUserService;

    @PostMapping
    public CommonResult<TokenInfo> login(@Valid @RequestBody LoginParamDTO paramDTO){
        TokenInfo info = sysUserService.login(paramDTO);
        return CommonResult.success(info);
    }

}
