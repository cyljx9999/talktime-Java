package com.qingmeng.strategy.login;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingmeng.adapt.TokenInfoAdapt;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.domain.dto.login.LoginParamDTO;
import com.qingmeng.domain.vo.login.TokenInfo;
import com.qingmeng.entity.SysUser;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.valid.AccountGroup;
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
    private SysUserDao sysUserDao;

    /**
     * 校验参数
     *
     * @param loginParamDTO 登陆参数类
     * @author qingmeng
     * @createTime: 2023/11/11 10:52:12
     */
    protected void checkParam(LoginParamDTO loginParamDTO) {
        AsserUtils.validateEntity(loginParamDTO,true, AccountGroup.class);
    }

    /**
     * 查询账号信息
     *
     * @param loginParamDTO 登陆参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/10 22:42:54
     */
    protected SysUser getAccountInfo(LoginParamDTO loginParamDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(loginParamDTO.getAccount()),SysUser::getUserAccount, loginParamDTO.getAccount());
        wrapper.eq(StrUtil.isNotBlank(loginParamDTO.getPassword()),SysUser::getUserPassword, loginParamDTO.getPassword());
        return sysUserDao.getOne(wrapper);
    }


    /**
     * 根据账号信息，生成token
     *
     * @param sysUser 用户信息对象
     * @param loginType 登陆类型
     * @param flag true 表示 登陆记住我，false 表示 登陆不记住我
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/11 00:33:31
     */
    protected TokenInfo createToken(SysUser sysUser,String loginType,boolean flag) {
        StpUtil.login(sysUser.getId(), new SaLoginModel()
                .setDevice(loginType)
                .setIsLastingCookie(flag)
                .setExtra("userName", sysUser.getUserName())
        );
        return TokenInfoAdapt.buildTokenInfo(StpUtil.getTokenInfo());
    }

}
