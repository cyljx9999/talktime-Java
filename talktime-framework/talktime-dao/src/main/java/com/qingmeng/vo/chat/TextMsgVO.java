package com.qingmeng.vo.chat;

import com.qingmeng.dto.chat.UrlInfo;
import com.qingmeng.vo.chat.child.ReplyMsg;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文本消息回复类
 * @createTime 2024年06月06日 11:26:00
 */
@Data
public class TextMsgVO {
    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息链接映射
     */
    private Map<String, UrlInfo> urlContentMap;
    /**
     * 艾特列表
     */
    private List<Long> atUserIdList;

    /**
     * 父消息，如果没有父消息，返回的是null
     */
    private ReplyMsg reply;
}
