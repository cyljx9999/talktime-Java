package com.qingmeng.config.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 表示 WebSocket 消息标记信息类
 * @createTime 2023年11月13日 10:38:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsMsgMarkVO {
    /**
     * 消息标记项列表
     */
    private List<WsMsgMarkItem> markList;

    /**
     * 表示 WebSocket 消息标记项信息类。
     */
    @Data
    public static class WsMsgMarkItem {

        /**
         * 操作者的唯一标识符
         */
        private Long userId;

        /**
         * 消息的唯一标识符
         */
        private Long msgId;

        /**
         * 标记类型：1 表示点赞，2 表示举报
         *
         * @see com.qingmeng.enums.chat.MessageMarkTypeEnum
         */
        private Integer markType;

        /**
         * 被标记的数量
         */
        private Long markCount;

        /**
         * 动作类型：1 表示确认，2 表示取消
         *
         * @see com.qingmeng.enums.chat.MessageMarkActTypeEnum
         */
        private Integer actType;
    }
}
