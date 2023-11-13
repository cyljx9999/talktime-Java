package com.qingmeng.service;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
public interface SysUserService{

    /**
     * 登陆
     *
     * @param paramDTO 登陆参数对象
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/11 00:49:54
     */
    TokenInfoVO login(LoginParamDTO paramDTO);

    /**
     * 获取验证码
     *
     * @return {@link CaptchaVO }
     * @author qingmeng
     * @createTime: 2023/11/11 14:27:50
     */
    CaptchaVO getCaptcha();

    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     * @author qingmeng
     * @createTime: 2023/11/11 21:14:04
     */
    void sendPhone(String phone);

    /**
     * 注册
     *
     * @param paramDTO 参数对象
     * @author qingmeng
     * @createTime: 2023/11/13 07:51:11
     */
    void register(RegisterDTO paramDTO);
}
