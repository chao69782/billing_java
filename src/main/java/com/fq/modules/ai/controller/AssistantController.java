package com.fq.modules.ai.controller;

import com.fq.api.api.controller.BaseController;
import com.fq.modules.ai.config.AssistantConfig;
import com.fq.modules.ai.tools.AssistantTools;
import com.fq.modules.chat.enums.ChatRole;
import com.fq.modules.chat.service.ChatHistoryService;
import com.fq.utils.user.UserUtils;
import dev.langchain4j.service.TokenStream;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author 超chao
 * @version 1.0
 * @date 2026/3/5 10:41
 * @description
 */
@Tag(name = "AI助手")
@RestController
@RequestMapping("/api/common/ai")
public class AssistantController extends BaseController {
    @Autowired
    private AssistantTools assistantTools;

    @Autowired
    private AssistantConfig.AssistantUniqueStore assistantUniqueStore;

    @Autowired
    private ChatHistoryService chatHistoryService;


    /**
     * 记忆对话流式
     * @param message
     * @return
     */
    @GetMapping(value = "/chat",produces = "text/stream;charset=UTF-8")
    @Operation(summary = "记忆对话流式")
    @PreAuthorize("isAuthenticated()")
    public Flux<String> memoryStreamTools(@RequestParam(defaultValue = "你好！") String message){
        Long userId = UserUtils.getUserId();
        // 保存用户消息
        chatHistoryService.saveHistory(userId, ChatRole.USER.getRole(), message);

        TokenStream stream = assistantUniqueStore.chatStream(userId, message, assistantTools.getCategoriesForPrompt());
        return Flux.create(fluxSink -> {
            stream.onPartialResponse(fluxSink::next)
                    .onCompleteResponse(chatResponse -> {
                        // 保存助手消息
                        chatHistoryService.saveHistory(userId, ChatRole.ASSISTANT.getRole(), chatResponse.aiMessage().text());
                        fluxSink.complete();
                    })
                    .onError(fluxSink::error)
                    .start();
        });
    }
}
