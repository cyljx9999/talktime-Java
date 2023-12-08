package com.qingmeng.config.event;

import com.qingmeng.entity.SysUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户注册事件
 * @createTime 2023年11月13日 08:23:00
 */
@Getter
public class SysUserRegisterEvent extends ApplicationEvent {
    private static final long serialVersionUID = -6899992731263171486L;
    private final SysUser sysUser;
    private final HttpServletRequest request;

    public SysUserRegisterEvent(Object source, SysUser param,HttpServletRequest request){
        super(source);
        this.sysUser = param;
        this.request = request;
    }
}
