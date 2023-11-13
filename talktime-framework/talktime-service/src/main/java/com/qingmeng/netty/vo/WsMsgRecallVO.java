package com.qingmeng.netty.vo;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息撤回实体类
 * @createTime 2023年11月13日 10:44:36
 */
@Data
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
     * 撤回的用户
     */
    private Long recallUid;
}
