package com.qingmeng.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.entity.SysUser;
import com.qingmeng.mapper.SysUserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月07日 09:41:00
 */
@RestController
public class TestController {
    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * @Description:
     * @param id
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/08 11:52:42
     */
    @GetMapping("test")
    @SysLog(title = "用户模块",content = "获取用户111")
    public Boolean getUser(Long id){
        return StpUtil.isLogin();
    }

    @GetMapping("test1")
    @SysLog(title = "用户模块",content = "获取用户111")
    public Boolean getUser1(){
        StpUtil.login(5, new SaLoginModel()
                .setDevice("pc")
                .setIsLastingCookie(true)
                .setExtra("userName", "name")
        );
        return true;
    }


}
