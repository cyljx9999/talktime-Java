package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息撤回实体类
 * @createTime 2023年11月13日 10:44:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMsgRecallVO {
    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 撤回的用户id
     */
    private Long recallUserId;
}
