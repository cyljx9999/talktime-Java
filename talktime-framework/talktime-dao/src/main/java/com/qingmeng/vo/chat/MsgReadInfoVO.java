package com.qingmeng.vo.chat;

import lombok.Data;

/**
 * @author qingmeng
 * @date 2024/06/09 19:01:41
 */
@Data
public class MsgReadInfoVO {

    /**
     * 消息 ID
     */
    private Long msgId;


    /**
     * 已读数
     */
    private Long readCount;


    /**
     * 未读数
     */
    private Long unReadCount;

}
