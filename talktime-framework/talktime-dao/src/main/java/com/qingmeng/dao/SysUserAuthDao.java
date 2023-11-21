package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysUserAuth;
import com.qingmeng.mapper.SysUserAuthMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方授权登陆表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Service
public class SysUserAuthDao extends ServiceImpl<SysUserAuthMapper, SysUserAuth>{

    /**
     * 通过openId查询授权信息
     *
     * @param openId 第三方应用唯一凭证
     * @return {@link SysUserAuth }
     * @author qingmeng
     * @createTime: 2023/11/13 15:05:22
     */
    public SysUserAuth getInfoByOpenId(String openId) {
        LambdaQueryWrapper<SysUserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserAuth::getOpenId, openId);
        return getOne(wrapper);
    }
}
