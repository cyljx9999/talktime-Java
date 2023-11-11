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

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆相关controller
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
     * @param paramDTO 登陆参数类
     * @return {@link CommonResult }<{@link TokenInfo }>
     * @author qingmeng
     * @createTime: 2023/11/11 10:55:30
     */
    @PostMapping("/login")
    public CommonResult<TokenInfo> login(@RequestBody LoginParamDTO paramDTO){
        TokenInfo info = sysUserService.login(paramDTO);
        return CommonResult.success(info);
    }

}
