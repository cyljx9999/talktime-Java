package com.qingmeng.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.user.AlterPersonalInfoDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    public SysUser getUserInfoByAccount(LoginParamDTO paramDTO) {
        return lambdaQuery()
                .eq(StrUtil.isNotBlank(paramDTO.getAccount()), SysUser::getUserAccount, paramDTO.getAccount())
                .eq(StrUtil.isNotBlank(paramDTO.getPassword()), SysUser::getUserPassword, paramDTO.getPassword())
                .one();
    }

    /**
     * 通过账号查询对应用户所有信息
     *
     * @param account 账户
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 07:54:54
     */
    public SysUser getUserInfoByAccount(String account) {
        return lambdaQuery().eq(SysUser::getUserAccount, account).one();
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
        return lambdaQuery().eq(SysUser::getUserPhone, phone).one();
    }

    /**
     * 更改账户
     *
     * @param userId            用户 ID
     * @param alterAccountCount 改名卡次数
     * @param userAccount       用户账户
     * @author qingmeng
     * @createTime: 2023/12/02 10:19:54
     */
    public void alterAccount(Long userId,Integer alterAccountCount,String userAccount) {
        lambdaUpdate()
                .eq(SysUser::getId,userId)
                .set(SysUser::getAlterAccountCount,alterAccountCount)
                .set(SysUser::getUserAccount,userAccount)
                .update(new SysUser());
    }

    /**
     * 更改个人信息
     *
     * @param userId                      用户 ID
     * @param alterAccountPersonalInfoDTO 更改账户个人信息 DTO
     * @author qingmeng
     * @createTime: 2023/12/02 10:45:14
     */
    public void alterPersonalInfo(Long userId, AlterPersonalInfoDTO alterAccountPersonalInfoDTO) {
        lambdaUpdate()
                .eq(SysUser::getId,userId)
                .set(StrUtil.isNotBlank(alterAccountPersonalInfoDTO.getUserName()),SysUser::getUserName, alterAccountPersonalInfoDTO.getUserName())
                .set(StrUtil.isNotBlank(alterAccountPersonalInfoDTO.getUserPhone()),SysUser::getUserPhone, alterAccountPersonalInfoDTO.getUserPhone())
                .set(StrUtil.isNotBlank(alterAccountPersonalInfoDTO.getUserAvatar()),SysUser::getUserAvatar, alterAccountPersonalInfoDTO.getUserAvatar())
                .set(Objects.nonNull(alterAccountPersonalInfoDTO.getUserSex()),SysUser::getUserSex, alterAccountPersonalInfoDTO.getUserSex())
                .update(new SysUser());
    }
}
