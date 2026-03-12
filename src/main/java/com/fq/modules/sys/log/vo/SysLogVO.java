package com.fq.modules.sys.log.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 超 chao
 * @Description 系统日志 VO 类
 * @Date 2024/12/5/周四 14:23
 * @Version 1.0
 */
@Data
@Schema(title = "系统日志视图类")
public class SysLogVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @Schema(description = "日志主键")
    private Long id;

    /**
     * 用户 ID
     */
    @Schema(description = "用户 ID")
    private Long userId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 操作人姓名
     */
    @Schema(description = "操作人姓名")
    private String operator;

    /**
     * 请求 URL
     */
    @Schema(description = "请求 URL")
    private String requestUrl;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String params;

    /**
     * 返回参数
     */
    @Schema(description = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0 正常 其他异常）
     */
    @Schema(description = "操作状态")
    private Integer status;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String errorMsg;

    /**
     * 响应时间
     */
    @Schema(description = "响应时间")
    private Long responseTime;

    /**
     * 平台类型
     */
    @Schema(description = "平台类型")
    private String platform;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    private Date createTime;
}
