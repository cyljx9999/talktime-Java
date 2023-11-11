package com.qingmeng.strategy.login;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.domain.dto.login.LoginParamDTO;
import com.qingmeng.domain.vo.login.TokenInfo;
import com.qingmeng.entity.SysUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 手机登陆实现类
 * @createTime 2023年11月11日 00:15:00
 */
@Component
public class PhoneStrategy extends AbstractLoginStrategy{
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 查询账号信息
     *
     * @param loginParamDTO 登陆参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/10 22:42:54
     */
    @Override
    protected SysUser getAccountInfo(LoginParamDTO loginParamDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(loginParamDTO.getPhone()),SysUser::getUserPhone, loginParamDTO.getPhone());
        return sysUserDao.getOne(wrapper);
    }

    /**
     * 登陆获取token方法
     *
     * @param loginParamDTO 登陆参数
     * @return {@link TokenInfo }
     * @author qingmeng
     * @createTime: 2023/11/10 22:40:40
     */
    @Override
    public TokenInfo getTokenInfo(LoginParamDTO loginParamDTO) {
        SysUser sysUser = getAccountInfo(loginParamDTO);
        return createToken(sysUser,loginParamDTO.getLoginType(),loginParamDTO.getFlag());
    }
}
