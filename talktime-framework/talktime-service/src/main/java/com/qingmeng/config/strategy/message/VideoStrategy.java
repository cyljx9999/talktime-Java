package com.qingmeng.config.strategy.message;

import cn.hutool.core.bean.BeanUtil;
import com.qingmeng.dao.ChatMessageDao;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.dto.chat.msg.MessageExtra;
import com.qingmeng.dto.chat.msg.VideoMsgDTO;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.utils.AssertUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 视频信息类
 * @createTime 2024年06月05日 21:39:00
 */
@Component
public class VideoStrategy extends AbstractMessageStrategy{

    @Resource
    private ChatMessageDao chatMessageDao;


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
        AssertUtils.validateEntity(messageDTO.getBody(),false);
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
        return msg.getExtra().getVideoMsgDTO();
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
        return "视频";
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
    public String showSessionMsg(ChatMessage msg) {
        return "[视频]";
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
        VideoMsgDTO videoMsgDTO = BeanUtil.toBean(messageDTO.getBody(), VideoMsgDTO.class);
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        ChatMessage update = new ChatMessage();
        update.setId(msg.getId());
        update.setExtra(extra);
        extra.setVideoMsgDTO(videoMsgDTO);
        chatMessageDao.updateById(update);
    }
}
