package com.qingmeng.dto.chat.msg;


import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 表情包参数类
 * @createTime 2024年06月04日 21:08:00
 */
@Data
public class EmojisMsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 网址
     */
    @NotBlank
    @URL
    private String url;
}


