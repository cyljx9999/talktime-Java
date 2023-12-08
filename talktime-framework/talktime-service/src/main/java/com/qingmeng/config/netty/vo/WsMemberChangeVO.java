package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 成员变动
 * @createTime 2023年11月13日 10:36:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsMemberChangeVO {
    /**
     * 群组唯一标识符
     */
    private Long roomId;

    /**
     * 变动的用户唯一标识符
     */
    private Long userId;

    /**
     * 变动类型：1 表示加入群组，2 表示移除群组
     * @see com.qingmeng.enums.chat.MemberChangeEnum
     */
    private Integer changeType;

    /**
     * 在线状态：0 表示离线 1 表示在线
     *
     * @see com.qingmeng.enums.user.UsageStatusEnum
     */
    private Integer activeStatus;

    /**
     * 最后一次上下线时间
     */
    private Date lastOptTime;
}
