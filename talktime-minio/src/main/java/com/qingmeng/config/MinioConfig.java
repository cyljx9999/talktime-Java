package com.qingmeng.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description minio配置
 * @createTime 2023年12月05日 21:14:00
 */
@Configuration
public class MinioConfig {

    @Resource
    private MinioProperties minioProperties;

    /**
     * Minio 客户端
     *
     * @return {@link MinioClient }
     * @author qingmeng
     * @createTime: 2023/12/05 21:17:33
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
