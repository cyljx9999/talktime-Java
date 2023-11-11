package com.qingmeng.strategy.login;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.utils.AsserUtils;
import com.qingmeng.utils.RedisUtils;
import com.qingmeng.valid.PhoneGroup;
import com.qingmeng.vo.login.TokenInfoVO;
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
     * 校验参数
     *
     * @param loginParamDTO 登陆参数
     * @author qingmeng
     * @createTime: 2023/11/11 11:16:16
     */
    @Override
    protected void checkParam(LoginParamDTO loginParamDTO) {
        AsserUtils.validateEntity(loginParamDTO,true, PhoneGroup.class);
        String phoneCode = RedisUtils.get(RedisConstant.PHONE_CODE_EXPIRE + loginParamDTO.getPhone());
        if (SystemConstant.UNIVERSAL_VERIFICATION_CODE.equals(phoneCode)){
            return;
        }
        AsserUtils.equal(phoneCode, loginParamDTO.getCode(),"验证码不一致");
    }

    /**
     * 查询账号信息
     *
     * @param loginParamDTO 登陆参数类
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/10 22:42:54
     */
    @Override
    protected SysUser getUserInfo(LoginParamDTO loginParamDTO) {
        return sysUserDao.getUserInfoByPhone(loginParamDTO);
    }

    /**
     * 登陆获取token方法
     *
     * @param loginParamDTO 登陆参数
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/10 22:40:40
     */
    @Override
    public TokenInfoVO getTokenInfo(LoginParamDTO loginParamDTO) {
        checkParam(loginParamDTO);
        SysUser sysUser = getUserInfo(loginParamDTO);
        return createToken(sysUser,loginParamDTO.getLoginType(),loginParamDTO.getFlag());
    }
}
