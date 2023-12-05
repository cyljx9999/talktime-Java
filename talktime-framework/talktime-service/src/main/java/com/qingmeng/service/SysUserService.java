package com.qingmeng.service;

import com.qingmeng.dto.login.LoginParamDTO;
import com.qingmeng.dto.login.RegisterDTO;
import com.qingmeng.dto.user.AlterAccountDTO;
import com.qingmeng.dto.user.AlterPersonalInfoDTO;
import com.qingmeng.dto.user.PersonalPrivacySettingDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;
import com.qingmeng.vo.user.CheckLoginVO;
import com.qingmeng.vo.user.ClickFriendInfoVo;
import com.qingmeng.vo.user.PersonalInfoVO;
import com.qingmeng.vo.user.PersonalPrivacySettingVO;

import javax.servlet.http.HttpServletRequest;

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
     * 更改帐户
     *
     * @param userId          用户 ID
     * @param alterAccountDTO 更改帐户 DTO
     * @author qingmeng
     * @createTime: 2023/11/23 21:45:01
     */
    void alterAccount(Long userId,AlterAccountDTO alterAccountDTO);

    /**
     * 更改个人信息
     *
     * @param userId                      用户 ID
     * @param alterAccountPersonalInfoDTO 更改帐户个人信息 DTO
     * @author qingmeng
     * @createTime: 2023/12/02 10:39:55
     */
    void alterPersonalInfo(Long userId, AlterPersonalInfoDTO alterAccountPersonalInfoDTO);

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
     * 删除好友
     *
     * @param userId   用户 ID
     * @param friendId 好友 ID
     * @author qingmeng
     * @createTime: 2023/12/01 09:03:23
     */
    void deleteFriend(Long userId, Long friendId);

    /**
     * 点击获取好友信息
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link ClickFriendInfoVo }
     * @author qingmeng
     * @createTime: 2023/12/02 09:52:10
     */
    ClickFriendInfoVo getFriendInfoByClick(Long userId, Long friendId);

    /**
     * 获取个人隐私设置
     *
     * @param userId 用户 ID
     * @return {@link PersonalPrivacySettingVO }
     * @author qingmeng
     * @createTime: 2023/12/02 10:49:28
     */
    PersonalPrivacySettingVO getPersonalPrivacySetting(Long userId);

    /**
     * 更改个人隐私设置
     *
     * @param userId                    用户 ID
     * @param personalPrivacySettingDTO 个人隐私设置 DTO
     * @author qingmeng
     * @createTime: 2023/12/02 11:31:39
     */
    void alterPersonalPrivacySetting(Long userId, PersonalPrivacySettingDTO personalPrivacySettingDTO);

    /**
     * 检查登录
     *
     * @return {@link CheckLoginVO }
     * @author qingmeng
     * @createTime: 2023/12/03 09:35:06
     */
    CheckLoginVO checkLogin();

}
