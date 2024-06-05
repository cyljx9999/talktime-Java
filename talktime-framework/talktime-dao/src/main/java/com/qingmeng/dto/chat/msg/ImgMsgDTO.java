package com.qingmeng.dto.chat.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 图片参数类
 * @createTime 2024年06月04日 21:08:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImgMsgDTO extends BaseFileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 宽度
     */
    @NotNull
    private Integer width;

    /**
     * 高度
     */
    @NotNull
    private Integer height;

}


