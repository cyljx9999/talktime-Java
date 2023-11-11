package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Service
public class SysUserDao extends ServiceImpl<SysUserMapper, SysUser> {

    /**
     * 通过账号查询对应用户所有信息
     *
     * @param paramDTO 参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/11 13:02:43
     */
    public SysUser getUserInfoByAccount(LoginParamDTO paramDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(paramDTO.getAccount()),SysUser::getUserAccount, paramDTO.getAccount());
        wrapper.eq(StrUtil.isNotBlank(paramDTO.getPassword()),SysUser::getUserPassword, paramDTO.getPassword());
        return getOne(wrapper);
    }

    /**
     * 通过手机号查询对应用户所有信息
     *
     * @param loginParamDTO 参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/11 13:05:51
     */
    public SysUser getUserInfoByPhone(LoginParamDTO loginParamDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(loginParamDTO.getPhone()),SysUser::getUserPhone, loginParamDTO.getPhone());
        return getOne(wrapper);
    }
}
