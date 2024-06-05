package com.qingmeng.dto.chat.msg;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qingmeng.dto.chat.UrlInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 额外拓展消息类
 * @createTime 2024年06月04日 21:08:00
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageExtra implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * url跳转链接
     */
    private Map<String, UrlInfo> urlContentMap;

    /**
     * 消息撤回详情
     */
    private MsgRecall recall;

    /**
     * 艾特的uid
     */
    private List<Long> atUidList;

    /**
     * 文件消息
     */
    private FileMsgDTO fileMsg;

    /**
     * 图片消息
     */
    private ImgMsgDTO imgMsgDTO;

    /**
     * 语音消息
     */
    private SoundMsgDTO soundMsgDTO;

    /**
     * 视频消息
     */
    private VideoMsgDTO videoMsgDTO;

    /**
     * 表情图片信息
     */
    private EmojisMsgDTO emojisMsgDTO;
}
