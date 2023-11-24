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
     * 通过账号密码查询对应用户所有信息
     *
     * @param paramDTO 参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 07:54:17
     */
    public SysUser getUserInfoByAccountAndPassword(LoginParamDTO paramDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(paramDTO.getAccount()),SysUser::getUserAccount, paramDTO.getAccount());
        wrapper.eq(StrUtil.isNotBlank(paramDTO.getPassword()),SysUser::getUserPassword, paramDTO.getPassword());
        return getOne(wrapper);
    }

    /**
     * 通过账号查询对应用户所有信息
     *
     * @param account 帐户
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 07:54:54
     */
    public SysUser getUserInfoByAccountAndPassword(String account) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(account),SysUser::getUserAccount, account);
        return getOne(wrapper);
    }

    /**
     * 通过手机号查询对应用户所有信息
     *
     * @param phone 电话
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 07:57:06
     */
    public SysUser getUserInfoByPhone(String phone) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(phone),SysUser::getUserPhone, phone);
        return getOne(wrapper);
    }

    /**
     * 佩戴物品
     *
     * @param userId    用户 ID
     * @param articleId 物品 ID
     * @author qingmeng
     * @createTime: 2023/11/24 22:22:53
     */
    public void wearArticle(Long userId, Long articleId) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setArticleId(articleId);
        updateById(user);
    }

    /**
     * 更改帐户
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2023/11/24 22:28:22
     */
    public void alterAccount(Long userId) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setAlterAccountCount(user.getAlterAccountCount() - 1);
        updateById(user);
    }
}
