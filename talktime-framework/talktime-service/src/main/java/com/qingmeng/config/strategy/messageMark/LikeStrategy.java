package com.qingmeng.config.strategy.messageMark;


import com.qingmeng.enums.chat.MessageMarkTypeEnum;
import com.qingmeng.enums.chat.MessageMarkTypeMethodEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 喜欢策略
 *
 * @author qingmeng
 * @date 2024/06/08 19:17:15
 */
@Component
public class LikeStrategy extends AbstractMessageMarkStrategy {
    @Resource
    @Lazy
    private MessageMarkFactory messageMarkFactory;

    @Override
    protected MessageMarkTypeEnum getTypeEnum() {
        return MessageMarkTypeEnum.UPVOTE;
    }


    @Override
    public void doMark(Long uid, Long msgId) {
        super.doMark(uid, msgId);
        // 同时取消点踩的动作
        String strategy = MessageMarkTypeMethodEnum.DIS_LIKE.getValue();
        messageMarkFactory.getStrategyWithType(strategy).cancelMark(uid, msgId);
    }
}
