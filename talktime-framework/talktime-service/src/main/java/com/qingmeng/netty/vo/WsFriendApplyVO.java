package com.qingmeng.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ws好友申请
 * @createTime 2023年11月13日 10:35:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsFriendApplyVO {
    /**
     * 用户唯一表示
     */
    private Long userId;

    /**
     * 申请未读数
     */
    private Integer unreadCount;
}
