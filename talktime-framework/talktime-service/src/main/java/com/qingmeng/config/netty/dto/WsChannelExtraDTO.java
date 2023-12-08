package com.qingmeng.config.netty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 记录和前端连接的一些映射信息
 * @createTime 2023年11月13日 11:36:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsChannelExtraDTO {
    /**
     * 用户唯一标识
     */
    private Long userId;
}
