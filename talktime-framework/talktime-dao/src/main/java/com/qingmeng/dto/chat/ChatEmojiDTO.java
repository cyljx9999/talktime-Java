package com.qingmeng.dto.chat;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 表情包参数
 * @createTime 2024年06月15日 13:28:00
 */
@Data
public class ChatEmojiDTO {

    /**
     * 表情包URL
     */
    @URL
    @NotBlank
    private String expressionUrl;

    /**
     * 标记 ID
     */
    @NotNull
    private Long tagId;

}
