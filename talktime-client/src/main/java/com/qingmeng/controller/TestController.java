package com.qingmeng.controller;

import com.qingmeng.entity.User;
import com.qingmeng.mapper.UserMapper;
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
    private UserMapper userMapper;

    @GetMapping("/getUserInfo")
    public User getUserInfo(){
        return userMapper.selectById(11007);
    }

}
