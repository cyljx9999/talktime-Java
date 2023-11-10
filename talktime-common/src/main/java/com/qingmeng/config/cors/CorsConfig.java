package com.qingmeng.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 跨域配置
 * @createTime 2023年11月10日 10:39:00
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
