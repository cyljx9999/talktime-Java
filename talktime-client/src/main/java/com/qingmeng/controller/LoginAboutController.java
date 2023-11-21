package com.qingmeng.controller;

import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.service.SysUserService;
import com.qingmeng.valid.AddGroup;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
     * @return {@link CommonResult }<{@link TokenInfoVO }>
     * @author qingmeng
     * @createTime: 2023/11/11 11:17:45
     */
    @PostMapping("/login")
    public CommonResult<TokenInfoVO> login(@RequestBody LoginParamDTO paramDTO){
        TokenInfoVO info = sysUserService.login(paramDTO);
        return CommonResult.success(info);
    }

    /**
     * 获取验证码
     *
     * @return {@link CommonResult }<{@link CaptchaVO }>
     * @author qingmeng
     * @createTime: 2023/11/11 14:51:40
     */
    @GetMapping("/getCaptcha")
    public CommonResult<CaptchaVO> getCaptcha(){
        CaptchaVO captchaVO = sysUserService.getCaptcha();
        return CommonResult.success(captchaVO);
    }

    /**
     * 发手机验证码
     *
     * @param phone 手机号
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/11 21:23:41
     */
    @PostMapping("/sendPhoneCode/{phone}")
    public CommonResult<String> sendPhoneCode(@PathVariable String phone){
        sysUserService.sendPhone(phone);
        return CommonResult.success();
    }

    /**
     * 注册账号
     *
     * @param paramDTO 参数对象
     * @param request 请求
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/13 07:50:51
     */
    @PostMapping("/register")
    public CommonResult<String> register(@Validated({AddGroup.class}) @RequestBody RegisterDTO paramDTO, HttpServletRequest request){
        sysUserService.register(paramDTO,request);
        return CommonResult.success();
    }

}
