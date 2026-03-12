package com.fq.modules.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fq.modules.chat.entity.ChatHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 超chao
 * @description 聊天记录Mapper接口
 */
@Mapper
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {
}
