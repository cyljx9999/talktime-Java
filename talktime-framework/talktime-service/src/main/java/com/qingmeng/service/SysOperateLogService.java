package com.qingmeng.service;

import com.qingmeng.entity.SysOperateLog;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-10 11:10:57
 */
public interface SysOperateLogService{

    /**
     * 保存日志
     *
     * @param sysOperateLog 日志对象数据
     * @author qingmeng
     * @createTime: 2023/11/10 15:18:05
     */
    void save(SysOperateLog sysOperateLog);
}
