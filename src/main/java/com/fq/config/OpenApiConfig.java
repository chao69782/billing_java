package com.fq.config;

import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GlobalOperationCustomizer globalOperationCustomizer() {
        return (operation, handlerMethod) -> {
            String className = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            // 统一生成 operationId，格式为：类名_方法名，避免不同 Controller 中同名方法导致 OpenAPI/Knife4j 接口覆盖问题
            operation.setOperationId(className + "_" + methodName);
            return operation;
        };
    }
}
