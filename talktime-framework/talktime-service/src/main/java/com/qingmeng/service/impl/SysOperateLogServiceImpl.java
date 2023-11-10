package com.qingmeng.service.impl;

import com.qingmeng.dao.SysOperateLogDao;
import com.qingmeng.entity.SysOperateLog;
import com.qingmeng.service.SysOperateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:18:00
 */
@Service
public class SysOperateLogServiceImpl implements SysOperateLogService {
    @Resource
    private SysOperateLogDao sysOperateLogDao;

    /**
     * 保存日志
     *
     * @param sysOperateLog 日志对象数据
     * @author qingmeng
     * @createTime: 2023/11/10 15:18:05
     */
    @Override
    public void save(SysOperateLog sysOperateLog) {
        sysOperateLogDao.save(sysOperateLog);
    }
}
