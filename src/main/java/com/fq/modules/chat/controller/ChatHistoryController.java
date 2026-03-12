package com.fq.modules.chat.controller;

import com.fq.api.api.ApiRest;
import com.fq.api.api.controller.BaseController;
import com.fq.modules.chat.entity.ChatHistory;
import com.fq.modules.chat.service.ChatHistoryService;
import com.fq.utils.user.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 超chao
 * @description 聊天记录控制器
 */
@Tag(name = "聊天记录")
@RestController
@RequestMapping("/api/front/chat")
public class ChatHistoryController extends BaseController {

    @Autowired
    private ChatHistoryService chatHistoryService;

    @GetMapping("/list")
    @Operation(summary = "查询聊天记录列表")
    public ApiRest<List<ChatHistory>> list() {
        Long userId = UserUtils.getUserId();
        return super.success(chatHistoryService.list(userId));
    }
}
