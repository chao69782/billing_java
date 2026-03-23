package com.fq.modules.ai.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.fq.ability.redis.constant.RedisConstant;
import com.fq.ability.redis.service.RedisService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author 超 chao
 * @version 1.0
 * @date 2026/3/5 10:38
 * @description 基于 Redis 的持久化聊天记忆存储
 */
@Component
public class PersistentChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private RedisService redisService;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        if (memoryId == null) {
            return Collections.emptyList();
        }
        String key = RedisConstant.CHAT_MEMORY_PREFIX + memoryId;
        String json = redisService.getString(key);
        if (json == null) {
            return Collections.emptyList();
        }
        
        try {
            // 使用 SupportAutoType 支持多态反序列化 (UserMessage, AiMessage 等)
            // 这里的 Filter 是为了安全，只允许 langchain4j 的包
            // 使用 FieldBased 以支持没有 getter/setter 的字段
            return JSON.parseObject(json, new TypeReference<List<ChatMessage>>(){},
                    JSONReader.autoTypeFilter("dev.langchain4j.data.message."),
                    JSONReader.Feature.SupportAutoType,
                    JSONReader.Feature.FieldBased);
        } catch (Exception e) {
            // 如果反序列化失败（可能是旧数据格式不兼容），返回空列表
            // 可以在这里选择删除损坏的 key
            redisService.del(key);
            return Collections.emptyList();
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        if (memoryId == null) {
            return;
        }
        String key = RedisConstant.CHAT_MEMORY_PREFIX + memoryId;
        // 序列化时写入类名 (WriteClassName) 以支持多态
        // 使用 FieldBased 以支持私有字段
        String json = JSON.toJSONString(messages,
                JSONWriter.Feature.WriteClassName,
                JSONWriter.Feature.FieldBased);
        redisService.set(key, json, RedisConstant.CHAT_MEMORY_EXPIRE);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        if (memoryId == null) {
            return;
        }
        String key = RedisConstant.CHAT_MEMORY_PREFIX + memoryId;
        redisService.del(key);
    }
}