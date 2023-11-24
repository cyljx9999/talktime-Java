package com.qingmeng.service;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import com.qingmeng.vo.user.PersonalInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @param request 请求
     * @author qingmeng
     * @createTime: 2023/11/13 07:51:11
     */
    void register(RegisterDTO paramDTO, HttpServletRequest request);

    /**
     * 使用 ID 获取用户信息
     *
     * @param userId 用户 ID
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/20 08:39:08
     */
    SysUser getUserInfoWithId(Long userId);

    /**
     * 保存
     *
     * @param saveUser 用户
     * @return boolean
     * @author qingmeng
     * @createTime: 2023/11/20 08:41:14
     */
    boolean save(SysUser saveUser);

    /**
     * 使用 ID 更新
     *
     * @param update 更新
     * @author qingmeng
     * @createTime: 2023/11/20 08:53:22
     */
    void updateWithId(SysUser update);

    /**
     * 按账号获取用户信息
     *
     * @param loginParamDTO login param dto
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/11/22 07:42:34
     */
    SysUser getUserInfoByAccount(LoginParamDTO loginParamDTO);

    /**
     * 根据ids查询用户集合
     *
     * @param userIds 用户 ID
     * @return {@link List }<{@link SysUser }>
     * @author qingmeng
     * @createTime: 2023/11/22 09:28:38
     */
    List<SysUser> listByIds(List<Long> userIds);

    /**
     * 更改帐户
     *
     * @param userId          用户 ID
     * @param alterAccountDTO 更改帐户 DTO
     * @author qingmeng
     * @createTime: 2023/11/23 21:45:01
     */
    void alterAccount(Long userId,AlterAccountDTO alterAccountDTO);

    /**
     * 获取个人信息
     *
     * @param userId 用户 ID
     * @return {@link PersonalInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/23 21:55:38
     */
    PersonalInfoVO getPersonalInfo(Long userId);

    /**
     * 佩戴物品
     *
     * @param userId    用户 ID
     * @param articleId 物品 ID
     * @author qingmeng
     * @createTime: 2023/11/24 22:19:16
     */
    void wearArticle(Long userId, Long articleId);
}
