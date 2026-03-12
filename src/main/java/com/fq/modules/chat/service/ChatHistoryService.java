package com.fq.modules.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fq.modules.chat.entity.ChatHistory;

import java.util.List;

/**
 * @author 超chao
 * @description 聊天记录服务类
 */
public interface ChatHistoryService extends IService<ChatHistory> {
    
    /**
     * 保存聊天记录
     * @param userId 用户ID
     * @param role 角色(user/assistant)
     * @param content 内容
     */
    void saveHistory(Long userId, String role, String content);

    /**
     * 查询聊天记录列表
     * @param userId 用户ID
     * @return 聊天记录列表
     */
    List<ChatHistory> list(Long userId);
}
