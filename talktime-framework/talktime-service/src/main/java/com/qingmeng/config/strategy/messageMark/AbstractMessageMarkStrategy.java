package com.qingmeng.config.strategy.messageMark;

import com.qingmeng.config.adapt.ChatMessageAdapter;
import com.qingmeng.config.event.MessageMarkEvent;
import com.qingmeng.dao.ChatMessageMarkDao;
import com.qingmeng.dto.chat.ChatMessageOtherMarkDTO;
import com.qingmeng.entity.ChatMessageMark;
import com.qingmeng.enums.chat.MessageMarkActTypeEnum;
import com.qingmeng.enums.chat.MessageMarkTypeEnum;
import com.qingmeng.enums.common.YesOrNoEnum;
import com.qingmeng.exception.TalkTimeException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 抽象信息类
 * @createTime 2024年06月04日 21:33:00
 */
@Component
public abstract class AbstractMessageMarkStrategy implements MessageMarkStrategy {
    @Resource
    private ChatMessageMarkDao chatMessageMarkDao;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 获取类型枚举
     *
     * @return {@link MessageMarkTypeEnum }
     * @author qingmeng
     * @createTime: 2024/06/08 13:35:48
     */
    protected abstract MessageMarkTypeEnum getTypeEnum();

    /**
     * 标记
     *
     * @param userId 用户 ID
     * @param msgId  消息 ID
     * @author qingmeng
     * @createTime: 2024/06/08 13:32:32
     */
    @Override
    public void mark(Long userId, Long msgId) {
        doMark(userId, msgId);
    }

    /**
     * 取消标记
     *
     * @param userId 用户 ID
     * @param msgId  消息 ID
     * @author qingmeng
     * @createTime: 2024/06/08 13:32:34
     */
    @Override
    public void cancelMark(Long userId, Long msgId) {
        doCancelMark(userId, msgId);
    }

    protected void doMark(Long userId, Long msgId) {
        exec(userId, msgId, MessageMarkActTypeEnum.CONFIRM);
    }

    protected void doCancelMark(Long userId, Long msgId) {
        exec(userId, msgId, MessageMarkActTypeEnum.CANCEL);
    }

    protected void exec(Long userId, Long msgId, MessageMarkActTypeEnum actTypeEnum) {
        Integer markType = getTypeEnum().getCode();
        Integer actType = actTypeEnum.getCode();
        ChatMessageMark oldMark = chatMessageMarkDao.get(userId, msgId, markType);
        if (Objects.isNull(oldMark) && actTypeEnum == MessageMarkActTypeEnum.CANCEL) {
            // 取消的类型，数据库一定有记录，没有就直接跳过操作
            return;
        }
        //插入一条新消息,或者修改一条消息
        ChatMessageMark insertOrUpdate = ChatMessageAdapter.getChatMessageMark(
                Optional.ofNullable(oldMark).map(ChatMessageMark::getId).orElse(null),
                userId,
                markType,
                msgId,
                transformAct(actType)
        );
        boolean modify = chatMessageMarkDao.saveOrUpdate(insertOrUpdate);
        if (modify) {
            //修改成功才发布消息标记事件
            ChatMessageOtherMarkDTO dto = new ChatMessageOtherMarkDTO(userId, msgId, markType, actType);
            applicationEventPublisher.publishEvent(new MessageMarkEvent(this, dto));
        }
    }

    private Integer transformAct(Integer actType) {
        if (MessageMarkActTypeEnum.CONFIRM.getCode().equals(actType)) {
            return YesOrNoEnum.NO.getCode();
        } else if (MessageMarkActTypeEnum.CANCEL.getCode().equals(actType)) {
            return YesOrNoEnum.YES.getCode();
        }
        throw new TalkTimeException("动作类型 1确认 2取消");
    }

}
