package com.qingmeng.dto.chat.msg;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文本参数类
 * @createTime 2024年06月06日 11:13:00
 */
@Data
public class TextMsgDTO {
    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    @Size(max = 300, message = "消息内容过长")
    private String content;

    /**
     * 回复消息 ID
     */
    private Long replyMsgId;

    /**
     * 艾特好友的列表
     */
    @Size(max = 10, message = "一次最多允许艾特10个人")
    private List<Long> atUserIdList;
}
