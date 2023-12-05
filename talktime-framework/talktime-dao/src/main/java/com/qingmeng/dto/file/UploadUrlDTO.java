package com.qingmeng.dto.file;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文件上传参数类
 * @createTime 2023年12月05日 22:55:00
 */
@Data
public class UploadUrlDTO {
    /**
     * 文件名(带后缀)
     */
    @NotBlank
    private String fileName;

    /**
     * 上传场景 1聊天 2表情包 3二维码
     * @see com.qingmeng.enums.system.UploadSceneEnum
     */
    @NotNull
    private Integer uploadScene;
}
