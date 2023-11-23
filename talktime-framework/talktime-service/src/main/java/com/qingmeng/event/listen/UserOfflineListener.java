package com.qingmeng.event.listen;

import com.qingmeng.adapt.WsAdapter;
import com.qingmeng.cache.UserCache;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.user.UsageStatusEnum;
import com.qingmeng.event.UserOfflineEvent;
import com.qingmeng.netty.service.WebSocketService;
import com.qingmeng.service.SysUserService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户下线监听器
 * @createTime 2023年11月22日 23:26:00
 */
@Component
public class UserOfflineListener {
    @Resource
    private UserCache userCache;
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private SysUserService sysUserService;


    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void saveRedisAndPush(UserOfflineEvent event) {
        SysUser sysUser = event.getSysUser();
        userCache.offline(sysUser.getId(), sysUser.getLastOperateTime());
        // 推送给所有在线用户，该用户下线
        webSocketService.sendToAllOnline(WsAdapter.buildOfflineNotifyVO(sysUser), sysUser.getId());
    }

    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void updateUserInfo(UserOfflineEvent event) {
        SysUser sysUser = event.getSysUser();
        SysUser update = new SysUser();
        update.setId(sysUser.getId());
        update.setLastOperateTime(sysUser.getLastOperateTime());
        update.setOnlineStatus(UsageStatusEnum.OFF_LINE.getCode());
        sysUserService.updateWithId(update);
    }
}
