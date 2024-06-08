package com.qingmeng.config.strategy.message;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.qingmeng.config.cache.MsgCache;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.dto.chat.msg.MessageExtra;
import com.qingmeng.dto.chat.msg.TextMsgDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.entity.SysUser;
import com.qingmeng.enums.chat.ChatMessageStatusEnum;
import com.qingmeng.enums.chat.MessageTypeMethodEnum;
import com.qingmeng.enums.common.OperateEnum;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.vo.chat.TextMsgVO;
import com.qingmeng.vo.chat.child.ReplyMsg;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文本信息类
 * @createTime 2024年06月05日 21:39:00
 */
@Component
public class TextMsgStrategy extends AbstractMessageStrategy{

    @Resource
    private ChatMessageDao chatMessageDao;
    @Resource
    private UserCache userCache;
    @Resource
    private MsgCache msgCache;
    @Resource
    @Lazy
    private MessageFactory messageTypeFactory;


    /**
     * 检查消息
     *
     * @param messageDTO 消息 DTO
     * @param userId     用户 ID
     * @author qingmeng
     * @createTime: 2024/06/04 21:56:34
     */
    @Override
    public void checkMsg(ChatMessageDTO messageDTO, Long userId) {
        TextMsgDTO textMsgDTO = BeanUtil.toBean(messageDTO.getBody(), TextMsgDTO.class);
        AssertUtils.validateEntity(textMsgDTO,false);
        // 校验下回复消息
        if (Objects.nonNull(textMsgDTO.getReplyMsgId())) {
            ChatMessage replyMsg = chatMessageDao.getById(textMsgDTO.getReplyMsgId());
            AssertUtils.isNotEmpty(replyMsg, "回复消息不存在");
            AssertUtils.equal(replyMsg.getRoomId(), messageDTO.getRoomId(), "只能回复相同会话内的消息");
        }
        if (CollectionUtil.isNotEmpty(textMsgDTO.getAtUserIdList())) {
            // 前端传入的@用户列表可能会重复，需要去重
            List<Long> atUserIdList = textMsgDTO.getAtUserIdList().stream().distinct().collect(Collectors.toList());
            Map<Long, SysUser> batch = userCache.getBatch(atUserIdList);
            // 如果@用户不存在，userCache 返回的map中依然存在该key，但是value为null，需要过滤掉再校验
            long batchCount = batch.values().stream().filter(Objects::nonNull).count();
            AssertUtils.equal((long)atUserIdList.size(), batchCount, "艾特列表存成非群成员");
            boolean atAll = textMsgDTO.getAtUserIdList().contains(0L);
            if (atAll) {
                // todo 校验是否有权限@所有人
            }
        }
    }

    /**
     * 展示消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/06 10:43:24
     */
    @Override
    public Object showMsg(ChatMessage msg) {
        TextMsgVO textMsgVO = new TextMsgVO();
        textMsgVO.setContent(msg.getContent());
        textMsgVO.setUrlContentMap(Optional.ofNullable(msg.getExtra()).map(MessageExtra::getUrlContentMap).orElse(null));
        textMsgVO.setAtUserIdList(Optional.ofNullable(msg.getExtra()).map(MessageExtra::getAtUserIdList).orElse(null));
        //回复消息
        Optional<ChatMessage> reply = Optional.ofNullable(msg.getReplyMsgId())
                .map(msgCache::getMsg)
                .filter(a -> Objects.equals(a.getStatus(), ChatMessageStatusEnum.NORMAL.getStatus()));
        if (reply.isPresent()) {
            ChatMessage replyMessage = reply.get();
            ReplyMsg replyMsgVO = new ReplyMsg();
            replyMsgVO.setId(replyMessage.getId());
            replyMsgVO.setUserId(replyMessage.getFromUserId());
            replyMsgVO.setMessageType(replyMessage.getMessageType());
            MessageTypeMethodEnum messageTypeMethodEnum = MessageTypeMethodEnum.get(replyMessage.getMessageType().toString());
            MessageStrategy strategyWithType = messageTypeFactory.getStrategyWithType(messageTypeMethodEnum.getValue());
            replyMsgVO.setBody(strategyWithType.showReplyMsg(replyMessage));
            SysUser replyUser = userCache.get(replyMessage.getFromUserId());
            replyMsgVO.setUserName(replyUser.getUserName());
            replyMsgVO.setCanCallback(OperateEnum.toStatus(Objects.nonNull(msg.getGapCount()) && msg.getGapCount() <= SystemConstant.CAN_CALLBACK_GAP_COUNT));
            replyMsgVO.setGapCount(msg.getGapCount());
            textMsgVO.setReply(replyMsgVO);
        }
        return textMsgVO;
    }

    /**
     * 被回复时——展示的消息
     *
     * @param msg 消息
     * @return {@link Object }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:24
     */
    @Override
    public Object showReplyMsg(ChatMessage msg) {
        return msg.getContent();
    }

    /**
     * 会话列表——展示的消息
     *
     * @param msg 消息
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/04 21:57:14
     */
    @Override
    public String showContactMsg(ChatMessage msg) {
        return msg.getContent();
    }

    /**
     * 保存额外消息
     *
     * @param msg        消息
     * @param messageDTO 消息 DTO
     * @author qingmeng
     * @createTime: 2024/06/04 21:59:46
     */
    @Override
    public void saveExtraMessage(ChatMessage msg, ChatMessageDTO messageDTO) {
        TextMsgDTO textMsgDTO = BeanUtil.toBean(messageDTO.getBody(), TextMsgDTO.class);
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        ChatMessage update = new ChatMessage();
        update.setId(msg.getId());
        // todo 敏感词过滤
        update.setContent(textMsgDTO.getContent());
        update.setExtra(extra);
        // 如果有回复消息
        if (Objects.nonNull(textMsgDTO.getReplyMsgId())) {
            Long gapCount = chatMessageDao.getGapCount(msg.getRoomId(), textMsgDTO.getReplyMsgId(), msg.getId());
            update.setGapCount(gapCount);
            update.setReplyMsgId(textMsgDTO.getReplyMsgId());
        }
        // todo 判断消息url跳转
        //Map<String, UrlInfo> urlContentMap = URL_TITLE_DISCOVER.getUrlContentMap(textMsgDTO.getContent());
        //extra.setUrlContentMap(urlContentMap);

        // 艾特功能
        if (CollectionUtil.isNotEmpty(textMsgDTO.getAtUserIdList())) {
            extra.setAtUserIdList(textMsgDTO.getAtUserIdList());
        }

        chatMessageDao.updateById(update);
    }
}
