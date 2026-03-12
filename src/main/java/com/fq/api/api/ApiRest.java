package com.fq.api.api;

import com.fq.api.exception.ServiceException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author 超chao
 * @Description 数据结果返回的封装
 * @Date 2024/10/12/周六 14:06
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Schema(title = "接口响应", description = "接口响应")
public class ApiRest<T> {

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String msg;
    /**
     * 响应代码
     */
    @Schema(description = "响应代码,0为成功,1为失败", required = true)
    private Integer code;

    /**
     * 请求或响应body
     */
    @Schema(description = "响应内容")
    protected T data;




    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code.equals(0);
    }

    /**
     * 构造函数
     *
     * @param error
     */
    public ApiRest(ServiceException error) {
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

    /**
     * 构造函数
     *
     * @param error
     */
    public ApiRest(ApiError error) {
        this.code = error.getCode();
        this.msg = error.msg;
    }
}
