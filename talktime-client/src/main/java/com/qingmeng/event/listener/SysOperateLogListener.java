package com.qingmeng.event.listener;

import com.qingmeng.dao.SysOperateLogDao;
import com.qingmeng.entity.SysOperateLog;
import com.qingmeng.event.SysOperateLogEvent;
import com.qingmeng.service.SysOperateLogService;
import com.qingmeng.utils.IpUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 日志异步记录监听器
 * @createTime 2023年11月10日 15:16:00
 */
@Component
public class SysOperateLogListener {
    @Resource
    private SysOperateLogDao sysOperateLogDao;

    @Async("visibleTaskExecutor")
    @EventListener(classes = SysOperateLogEvent.class)
    public void saveLog(SysOperateLogEvent event) {
        SysOperateLog sysOperateLog = event.getSysOperateLog();
        HttpServletRequest request = event.getRequest();
        // IP地址
        sysOperateLog.setIp(IpUtils.getIpAddr(request));
        // IP归属地
        sysOperateLog.setIpLocation(IpUtils.getIpHomeLocal(request));
        sysOperateLogDao.save(sysOperateLog);
    }
}
