package com.qingmeng.netty.dto;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 接受前端传递过来的ws请求类型
 * @createTime 2023年11月13日 09:29:00
 */
@Data
public class WsBaseDTO {
    /**
     * 请求类型 1.请求登录二维码，2心跳检测 3 授权
     * @see com.qingmeng.netty.enums.WSRequestTypeEnum
     */
    private String type;

    /**
     * 每个请求包具体的数据，类型不同结果不同
     */
    private String data;
}
