package com.fq.modules.sys.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 超chao
 * @Description 操作日志记录
 * @Date 2024/12/2/周一 16:52
 * @Version 1.0
 */
@Data
@TableName(value = "sys_log")
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 模块标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 请求URL
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * 请求参数
     */
    @TableField(value = "operate_param")
    private String operateParam;

    /**
     * 返回参数
     */
    @TableField(value = "json_result")
    private String jsonResult;

    /**
     * 操作状态（0正常 其他异常）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 响应时间
     */
    @TableField(value = "response_time")
    private Long responseTime;

    /**
     * 平台类型
     */
    @TableField(value = "platform")
    private String platform;

}
