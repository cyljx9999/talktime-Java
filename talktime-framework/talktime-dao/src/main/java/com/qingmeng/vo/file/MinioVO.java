package com.qingmeng.vo.file;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description minio参数类
 * @createTime 2023年12月05日 21:23:00
 */
@Data
public class MinioVO {
    /**
     * 上传网址
     */
    private String uploadUrl;

    /**
     * 下载网址
     */
    private String downloadUrl;
}
