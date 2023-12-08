package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群成员列表的成员信息
 * @createTime 2023年11月13日 10:34:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMemberVO {
    /**
     * 用户唯一标识符
     */
    private Long userId;

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
