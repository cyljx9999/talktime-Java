package com.qingmeng.controller;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.vo.login.TokenInfo;
import com.qingmeng.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆香菇按controller
 * @createTime 2023年11月11日 00:47:00
 */
@RestController
@RequestMapping("/talktime")
public class LoginAboutController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 登陆
     *
     * @param paramDTO 登陆参数
     * @return {@link CommonResult }<{@link TokenInfo }>
     * @author qingmeng
     * @createTime: 2023/11/11 11:17:45
     */
    @PostMapping("/login")
    public CommonResult<TokenInfo> login( @RequestBody LoginParamDTO paramDTO){
        TokenInfo info = sysUserService.login(paramDTO);
        return CommonResult.success(info);
    }

}
