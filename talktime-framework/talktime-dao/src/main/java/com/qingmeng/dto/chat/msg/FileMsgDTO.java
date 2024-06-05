package com.qingmeng.dto.chat.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文件消息类
 * @createTime 2024年06月04日 21:08:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileMsgDTO extends BaseFileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    @NotBlank
    private String fileName;

}
