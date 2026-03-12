package com.fq.modules.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fq.modules.chat.entity.ChatHistory;
import com.fq.modules.chat.mapper.ChatHistoryMapper;
import com.fq.modules.chat.service.ChatHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 超chao
 * @description 聊天记录服务实现类
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory> implements ChatHistoryService {

    @Override
    public void saveHistory(Long userId, String role, String content) {
        ChatHistory record = new ChatHistory();
        record.setUserId(userId);
        record.setRole(role);
        record.setContent(content);
        this.save(record);
    }

    @Override
    public List<ChatHistory> list(Long userId) {
        return this.lambdaQuery()
                .eq(ChatHistory::getUserId, userId)
                .orderByAsc(ChatHistory::getId)
                .list();
    }
}
