package com.qingmeng.adapt;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.qingmeng.vo.login.CaptchaVO;
import com.qingmeng.vo.login.TokenInfoVO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description token信息设适配类
 * @createTime 2023年11月10日 22:57:00
 */
public class LoginAboutAdapt {

    /**
     * 构造token信息类
     *
     * @param saTokenInfo satoken对象
     * @return {@link TokenInfoVO }
     * @author qingmeng
     * @createTime: 2023/11/10 22:59:23
     */
    public static TokenInfoVO buildTokenInfo(SaTokenInfo saTokenInfo){
        TokenInfoVO info = new TokenInfoVO();
        info.setTokenName(saTokenInfo.getTokenName());
        info.setTokenValue(saTokenInfo.getTokenValue());
        info.setTokenTimeout(saTokenInfo.getTokenTimeout());
        return info;
    }

    /**
     * 构造验证码返回类
     *
     * @param captcha 验证码base64字符串
     * @param uuid 验证码对应id
     * @return {@link CaptchaVO }
     * @author qingmeng
     * @createTime: 2023/11/11 14:45:28
     */
    public static CaptchaVO buildCaptchaVO(String captcha,String uuid){
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCode(captcha);
        captchaVO.setCodeId(uuid);
        return captchaVO;
    }

}
