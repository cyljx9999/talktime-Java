package com.qingmeng.dto.chat.msg;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 视频信息参数类
 * @createTime 2024年06月04日 21:08:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoMsgDTO extends BaseFileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 缩略图宽度
     */
    @NotNull
    private Integer thumbWidth;

    /**
     * 缩略图高度
     */
    @NotNull
    private Integer thumbHeight;

    /**
     * 缩略图大小
     */
    @NotNull
    private Long thumbSize;

    /**
     * 缩略图 URL
     */
    @NotBlank
    @URL
    private String thumbUrl;

}
