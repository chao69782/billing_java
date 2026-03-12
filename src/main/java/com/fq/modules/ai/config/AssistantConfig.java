package com.fq.modules.ai.config;

import com.fq.modules.ai.tools.AssistantTools;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 超chao
 * @version 1.0
 * @date 2026/3/5 10:29
 * @description
 */
@Configuration
public class AssistantConfig {
    @Resource
    private ChatModel myQwenChatModel;
    @Resource
    private StreamingChatModel myQwenStreamingChatModel;
    @Resource
    private AssistantTools assistantTools;


    /**
     * 文字聊天对话并存储
     */
    public interface AssistantUniqueStore {
        // 文本输出
        String chat(@MemoryId Long memoryId, @UserMessage String message);
        // 流式输出
        @SystemMessage(fromResource = "system-prompt.txt")
        TokenStream chatStream(@MemoryId Long memoryId, @UserMessage String message, @V("categories") String categories);
    }

    @Bean
    public AssistantUniqueStore assistantUniqueStore(PersistentChatMemoryStore store){
        ChatMemoryProvider memoryProvider = memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                .maxMessages(5)
                .chatMemoryStore(store)
                .build();

        return AiServices
                .builder(AssistantUniqueStore.class)
                .chatModel(myQwenChatModel)
                .streamingChatModel(myQwenStreamingChatModel)
                .chatMemoryProvider(memoryProvider)
                .tools(assistantTools)
                .build();
    }
}
