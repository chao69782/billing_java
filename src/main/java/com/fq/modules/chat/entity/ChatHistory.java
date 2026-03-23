package com.fq.modules.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chat_history")
@Schema(title = "ChatHistory对象", description = "聊天历史记录")
public class ChatHistory extends Model<ChatHistory> {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "角色(user/assistant/system)")
    @TableField("role")
    private String role;

    @Schema(description = "消息内容")
    @TableField("content")
    private String content;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;
}
