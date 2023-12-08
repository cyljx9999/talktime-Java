package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 登陆二维码连接
 * @createTime 2023年11月13日 10:36:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsLoginUrlVO {
    /**
     * 登陆二维码链接
     */
    private String loginUrl;
}
