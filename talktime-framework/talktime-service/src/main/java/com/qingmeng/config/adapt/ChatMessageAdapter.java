package com.qingmeng.config.adapt;

import cn.hutool.core.bean.BeanUtil;
import com.qingmeng.config.strategy.message.MessageTypeFactory;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.entity.ChatMessageMark;
import com.qingmeng.enums.chat.MessageMarkTypeEnum;
import com.qingmeng.enums.common.YesOrNoEnum;
import com.qingmeng.vo.chat.ChatMessageVO;
import com.qingmeng.vo.chat.child.Message;
import com.qingmeng.vo.chat.child.MessageMark;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 消息适配类
 * @createTime 2024年06月04日 22:11:00
 */
public class ChatMessageAdapter {
    @Resource
    private MessageTypeFactory messageTypeFactory;

    public static ChatMessage buildChatMessageSave(ChatMessageDTO chatMessageDTO, Long userId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(chatMessageDTO.getRoomId());
        chatMessage.setFromUserId(userId);
        chatMessage.setStatus(0);
        chatMessage.setMessageType(chatMessageDTO.getMessageType());
        return chatMessage;
    }


    /**
     * 构建批处理 msg vo
     *
     * @param chatMessages 聊天消息
     * @param msgMark      消息标志
     * @param receiveUid   接收 UID
     * @param msgMap       展示消息集合
     * @return {@link List }<{@link ChatMessageVO }>
     * @author qingmeng
     * @createTime: 2024/06/07 23:42:17
     */
    public static List<ChatMessageVO> buildBatchMsgVO(List<ChatMessage> chatMessages, List<ChatMessageMark> msgMark, Long receiveUid,Map<Integer, Object> msgMap) {
        Map<Long, List<ChatMessageMark>> markMap = msgMark.stream().collect(Collectors.groupingBy(ChatMessageMark::getMsgId));
        return chatMessages.stream().map(chatMessage -> {
                    ChatMessageVO chatMessageVO = new ChatMessageVO();
                    chatMessageVO.setFromUserId(chatMessage.getFromUserId());
                    chatMessageVO.setChatMessage(buildMessage(chatMessage, markMap.getOrDefault(chatMessage.getId(), new ArrayList<>()), receiveUid,msgMap.get(chatMessage.getMessageType())));
                    return chatMessageVO;
                })
                .sorted(Comparator.comparing(a -> a.getChatMessage().getSendTime()))
                .collect(Collectors.toList());
    }

    private static Message buildMessage(ChatMessage chatMessage, List<ChatMessageMark> marks, Long receiveUid,Object showMsg) {
        Message messageVO = new Message();
        BeanUtil.copyProperties(chatMessage, messageVO);
        messageVO.setSendTime(chatMessage.getCreateTime());
        messageVO.setBody(showMsg);
        //消息标记
        messageVO.setMessageMark(buildMsgMark(marks, receiveUid));
        return messageVO;
    }

    private static MessageMark buildMsgMark(List<ChatMessageMark> chatMessageMarks, Long receiveUid) {
        Map<Integer, List<ChatMessageMark>> typeMap = chatMessageMarks.stream().collect(Collectors.groupingBy(ChatMessageMark::getType));
        List<ChatMessageMark> likeMarks = typeMap.getOrDefault(MessageMarkTypeEnum.UPVOTE.getCode(), new ArrayList<>());
        List<ChatMessageMark> dislikeMarks = typeMap.getOrDefault(MessageMarkTypeEnum.REPORT.getCode(), new ArrayList<>());
        MessageMark mark = new MessageMark();
        mark.setLikeCount(likeMarks.size());
        mark.setUserLike(
                Optional.ofNullable(receiveUid)
                        .filter(userId -> likeMarks.stream().anyMatch(a -> Objects.equals(a.getUserId(), userId)))
                        .map(a -> YesOrNoEnum.YES.getCode())
                        .orElse(YesOrNoEnum.NO.getCode())
        );
        mark.setDislikeCount(dislikeMarks.size());
        mark.setUserDislike(
                Optional.ofNullable(receiveUid)
                        .filter(userId -> dislikeMarks.stream().anyMatch(a -> Objects.equals(a.getUserId(), userId)))
                        .map(a -> YesOrNoEnum.YES.getCode())
                        .orElse(YesOrNoEnum.NO.getCode())
        );
        return mark;
    }

}
