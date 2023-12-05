package com.qingmeng.dto.file;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description minio参数类
 * @createTime 2023年12月05日 21:23:00
 */
@Data
public class MinioDTO {
    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 用户 ID
     */
    private Long userId;
}
