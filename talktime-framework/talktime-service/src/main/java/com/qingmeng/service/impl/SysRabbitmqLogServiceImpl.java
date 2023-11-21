package com.qingmeng.service.impl;

import com.qingmeng.dao.SysRabbitmqLogDao;
import com.qingmeng.entity.SysRabbitmqLog;
import com.qingmeng.service.SysRabbitmqLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月21日 15:11:00
 */
@Service
public class SysRabbitmqLogServiceImpl implements SysRabbitmqLogService {
    @Resource
    private SysRabbitmqLogDao sysRabbitmqLogDao;

    /**
     * 使用 ID 更新
     *
     * @param sysRabbitmqLog sys rabbitMQ 日志
     * @author qingmeng
     * @createTime: 2023/11/21 15:14:44
     */
    @Override
    public void updateWithId(SysRabbitmqLog sysRabbitmqLog) {
        sysRabbitmqLogDao.updateById(sysRabbitmqLog);
    }
}
