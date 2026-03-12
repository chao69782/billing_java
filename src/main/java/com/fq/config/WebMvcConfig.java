package com.fq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 超chao
 * @Description 静态资源映射
 * @Date 2025/10/28/周二 16:47
 * @Version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.upload.path}")
    private String filePath;
    @Value("${file.web.url}")
    private String fileUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置文件访问路径
        registry.addResourceHandler(fileUrl)
                .addResourceLocations("file:" + filePath);
    }
}
