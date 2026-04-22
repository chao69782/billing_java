package com.fq.modules.ai.model;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 超chao
 * @version 1.0
 * @date 2026/3/10 15:43
 * @description
 */
@Data
@Configuration
public class QwenChatModelConfig {

    @Value("${langchain4j.community.dashscope.chat-model.api-key}")
    private String chatApiKey;
    @Value("${langchain4j.community.dashscope.chat-model.model-name}")
    private String chatModelName;

    // 大语言模型
    @Bean
    public QwenChatModel myQwenChatModel(){
        return QwenChatModel.builder()
                .apiKey(chatApiKey)
                .modelName(chatModelName)
                .build();
    }

    @Bean
    public QwenStreamingChatModel myQwenStreamingChatModel(){
        return QwenStreamingChatModel.builder()
                .apiKey(chatApiKey)
                .modelName(chatModelName)
                .build();
    }
}
