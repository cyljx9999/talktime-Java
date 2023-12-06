package com.qingmeng.dto.file;

import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 扫码二维码参数类
 * @createTime 2023年12月06日 10:54:00
 */
@Data
public class ScanQrcodeDTO {

    /**
     * 扫描类型 1 好友二维码 2 群聊二维码
     *
     * @see com.qingmeng.enums.system.ScanQrcodeEnum
     */
    @NotNull
    @IntListValue(values = {1, 2})
    private Integer scanType;

    /**
     * 网址
     */
    @URL
    @NotBlank
    private String url;

}
