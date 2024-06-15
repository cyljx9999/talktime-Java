package com.qingmeng.dto.chat;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 表情包参数
 * @createTime 2024年06月15日 13:28:00
 */
@Data
public class ChatEmojiTagDTO {

    /**
     * 标签名称
     */
    @NotNull
    @Length(min = 1,max = 5)
    private String tagName;

}
