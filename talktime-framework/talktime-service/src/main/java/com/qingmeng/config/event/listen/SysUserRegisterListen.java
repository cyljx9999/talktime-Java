package com.qingmeng.config.event.listen;

import com.qingmeng.dao.SysUserDao;
import com.qingmeng.entity.SysUser;
import com.qingmeng.config.event.SysUserRegisterEvent;
import com.qingmeng.utils.IpUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月13日 08:25:00
 */
@Component
public class SysUserRegisterListen {
    @Resource
    private SysUserDao sysUserDao;

    @Async("visibleTaskExecutor")
    @EventListener(classes = SysUserRegisterEvent.class)
    public void updateLocalIp(SysUserRegisterEvent event) {
        SysUser sysUser = event.getSysUser();
        HttpServletRequest request = event.getRequest();
        // IP归属地
        sysUser.setIpLocation(IpUtils.getIpHomeLocal(request));
        sysUserDao.updateById(sysUser);
    }
}
