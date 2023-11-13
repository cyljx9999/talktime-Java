package com.qingmeng.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ws黑名单
 * @createTime 2023年11月13日 10:34:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsBlackVO {
    /**
     * 用户唯一表示
     */
    private Long userId;
}