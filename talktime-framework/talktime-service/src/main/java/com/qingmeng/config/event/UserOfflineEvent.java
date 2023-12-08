package com.qingmeng.config.event;

import com.qingmeng.entity.SysUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户下线发布者
 * @createTime 2023年11月22日 23:24:00
 */
@Getter
public class UserOfflineEvent extends ApplicationEvent {

    private static final long serialVersionUID = -1588620088082458602L;
    private final SysUser sysUser;

    public UserOfflineEvent(Object source, SysUser sysUser) {
        super(source);
        this.sysUser = sysUser;
    }
}
