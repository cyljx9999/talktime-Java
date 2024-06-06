package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description rabbitmq信息记录实体类
 * @createTime 2023年11月21日 15:05:40
 */
@Data
@TableName("sys_rabbitmq_log")
public class SysRabbitmqLog implements Serializable {
    private static final long serialVersionUID = 6938460088823026625L;

    /**
     * 信息唯一标识id
     */
    @TableId
    private String msgId;

    /**
     * 操作数据
     */
    private String data;

    /**
     * 0 为正常队列信息 1为延迟队列消息, 2为交换机消息
     * @see com.qingmeng.enums.system.RabbitmqQueueTypeEnum
     */
    private Integer flag;

    /**
     * 0 消息投递中   1 投递成功   2投递失败
     * @see com.qingmeng.enums.system.RabbitmqSendEnum
     */
    private Integer status;

    /**
     * 队列名字
     */
    private String queue;

    /**
     * 路由关键
     */
    private String routingKey;

    /**
     * 交换机名字
     */
    private String exchange;

    /**
     * 重置次数，超过三次不进行发送
     */
    private Integer count;

    /**
     * 重试时间
     */
    private Date tryTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;

}
