package com.qingmeng.event;

import com.qingmeng.entity.SysOperateLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 15:14:00
 */
@Getter
public class SysOperateLogEvent extends ApplicationEvent {
    private static final long serialVersionUID = -928790594761412161L;
    private final SysOperateLog sysOperateLog;
    private final HttpServletRequest request;

    public SysOperateLogEvent(Object source, SysOperateLog sysOperateLog,HttpServletRequest request){
        super(source);
        this.sysOperateLog = sysOperateLog;
        this.request = request;
    }
}
