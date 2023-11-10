package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
