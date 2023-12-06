package com.qingmeng.strategy.login;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.adapt.LoginAboutAdapt;
import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.system.BannedEnum;
import com.qingmeng.service.SysUserService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.RedisUtils;
import com.qingmeng.valid.AccountGroup;
import com.qingmeng.vo.login.TokenInfoVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆抽象实现类
 * @createTime 2023年11月10日 22:41:00
 */
@Component
public abstract class AbstractLoginStrategy implements LoginStrategy{
    @Resource
    private SysUserService sysUserService;

    /**
     * 校验参数
     *
     * @param loginParamDTO 登陆参数
     * @author qingmeng
     * @createTime: 2023/11/11 11:16:16
     */
    protected void checkParam(LoginParamDTO loginParamDTO){
        AssertUtils.validateEntity(loginParamDTO,true, AccountGroup.class);
        String captchaCode = RedisUtils.get(RedisConstant.CAPTCHA_CODE_KEY + loginParamDTO.getCodeId());
        if (SystemConstant.UNIVERSAL_VERIFICATION_CODE.equals(captchaCode)){
            return;
        }
        AssertUtils.equal(captchaCode, loginParamDTO.getCode(),"验证码不一致");
    }

    /**
     * 查询账号信息
     *
     * @param loginParamDTO 登陆参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/10 22:42:54
     */
    protected SysUser getUserInfo(LoginParamDTO loginParamDTO) {
        SysUser sysUser = sysUserService.getUserInfoByAccount(loginParamDTO);
        String encryptPassword = SaSecureUtil.md5BySalt(loginParamDTO.getPassword(), SystemConstant.MD5_SALT);
        AssertUtils.equal(encryptPassword,sysUser.getUserPassword(),"密码不一致");
        return sysUser;
    }


    /**
     * 根据账号信息，生成token
     *
     * @param sysUser 用户信息对象
     * @param loginType 登陆类型
     * @param flag true 表示 登陆记住我，false 表示 登陆不记住我
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/11 00:33:31
     */
    protected TokenInfoVO createToken(SysUser sysUser, String loginType, boolean flag) {
        // 账号是否封禁
        StpUtil.checkDisable(sysUser.getId(), BannedEnum.LOGIN.getStatus());
        StpUtil.login(sysUser.getId(), new SaLoginModel()
                .setDevice(loginType)
                .setIsLastingCookie(flag)
                .setExtra("userName", sysUser.getUserName())
        );
        return LoginAboutAdapt.buildTokenInfo(StpUtil.getTokenInfo());
    }

}
