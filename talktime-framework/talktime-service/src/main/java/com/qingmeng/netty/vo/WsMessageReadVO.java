package com.qingmeng.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息阅读
 * @createTime 2023年11月13日 10:37:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsMessageReadVO {
    /**
     * 消息id
     */
    private Long msgId;

    /**
     *  阅读人数
     */
    private Integer readCount;
}
