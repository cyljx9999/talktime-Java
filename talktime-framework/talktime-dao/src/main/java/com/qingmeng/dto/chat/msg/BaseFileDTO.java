package com.qingmeng.dto.chat.msg;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 基础文件参数类
 * @createTime 2024年06月04日 21:08:00
 */
@Data
public class BaseFileDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 大小
     */
    @NotNull
    private Long size;

    /**
     * 网址
     */
    @NotBlank
    @URL
    private String url;
}
