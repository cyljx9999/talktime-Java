package com.qingmeng.event;

import com.qingmeng.entity.SysUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户上线事件
 * @createTime 2023年11月23日 16:10:00
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8715882656912272649L;
    private final SysUser sysUser;

    public UserOnlineEvent(Object source, SysUser sysUser) {
        super(source);
        this.sysUser = sysUser;
    }

}
