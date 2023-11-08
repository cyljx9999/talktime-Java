package com.qingmeng.controller;

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
    public SysUser getUser(Long id){
        return null;
    }
}
