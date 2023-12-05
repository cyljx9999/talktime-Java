package com.qingmeng.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description minio读取配置
 * @createTime 2023年12月05日 18:05:21
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * 访问端点
     */
    private String endpoint;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 存储桶
     */
    private String bucketName;
}
