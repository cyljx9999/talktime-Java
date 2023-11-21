package com.qingmeng.service;

import com.qingmeng.entity.SysRabbitmqLog;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月21日 15:10:00
 */
public interface SysRabbitmqLogService {
    /**
     * 使用 ID 更新
     *
     * @param sysRabbitmqLog sys rabbitMQ 日志
     * @author qingmeng
     * @createTime: 2023/11/21 15:14:44
     */
    void updateWithId(SysRabbitmqLog sysRabbitmqLog);
}
