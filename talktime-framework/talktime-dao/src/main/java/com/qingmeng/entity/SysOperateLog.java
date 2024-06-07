package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qingmeng.enums.common.OperateEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-10 11:10:57
 */
@Data
@TableName("sys_operate_log")
public class SysOperateLog implements Serializable {

    private static final long serialVersionUID = -7506546756202779017L;
    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String operateName;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求IP地址
     */
    private String ip;

    /**
     * IP归属地
     */
    private String ipLocation;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 方法响应参数
     */
    private String responseResult;

    /**
     * 操作状态（0正常 1异常）
     * @see OperateEnum
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    /**
     * 方法执行耗时（单位：毫秒）
     */
    private Long takeTime;
}
