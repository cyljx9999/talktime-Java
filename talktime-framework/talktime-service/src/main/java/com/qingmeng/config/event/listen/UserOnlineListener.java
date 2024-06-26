package com.qingmeng.config.event.listen;

import com.qingmeng.config.adapt.WsAdapter;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.config.netty.service.WebSocketService;
import com.qingmeng.config.netty.vo.WsBaseVO;
import com.qingmeng.config.netty.vo.WsOnlineOfflineNotifyVO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.user.OnlineStatusEnum;
import com.qingmeng.config.event.UserOnlineEvent;
import com.qingmeng.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户上线监听器
 * @createTime 2023年11月23日 16:12:00
 */
@Component
@Slf4j
public class UserOnlineListener {
    @Resource
    private UserCache userCache;
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private SysUserService sysUserService;

    @Async("visibleTaskExecutor")
    @EventListener(classes = UserOnlineEvent.class)
    public void saveRedisAndPush(UserOnlineEvent event) {
        SysUser sysUser = event.getSysUser();
        userCache.online(sysUser.getId(), sysUser.getLastOperateTime());
        //推送给所有在线用户，该用户登录成功
        WsBaseVO<WsOnlineOfflineNotifyVO> wsBaseVO = WsAdapter.buildOnlineNotifyVO(sysUser);
        wsBaseVO.getData().setOnlineNum(webSocketService.getOnlineNum());
        webSocketService.sendToAllOnline(wsBaseVO);
    }

    @Async("visibleTaskExecutor")
    @EventListener(classes = UserOnlineEvent.class)
    public void updateUserInfo(UserOnlineEvent event) {
        SysUser user = event.getSysUser();
        SysUser update = new SysUser();
        update.setId(user.getId());
        update.setLastOperateTime(user.getLastOperateTime());
        update.setIpLocation(user.getIpLocation());
        update.setOnlineStatus(OnlineStatusEnum.ON_LINE.getCode());
        sysUserService.updateWithId(update);
    }
}
