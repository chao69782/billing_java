package com.fq.api.exception;

import com.fq.api.api.ApiError;
import com.fq.api.api.ApiRest;
import lombok.Data;

/**
 * @Author 超chao
 * @Description TODO
 * @Date 2024/10/12/周六 14:01
 * @Version 1.0
 */
@Data
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String msg;

    /**
     * 从结果初始化
     *
     * @param apiRest
     */
    public ServiceException(ApiRest apiRest) {
        this.code = apiRest.getCode();
        this.msg = apiRest.getMsg();
    }

    /**
     * 从枚举中获取参数
     *
     * @param apiError
     */
    public ServiceException(ApiError apiError) {
        this.code = apiError.getCode();
        this.msg = apiError.msg;
    }

    /**
     * 通用的错误信息
     *
     * @param msg
     */
    public ServiceException(String msg) {
        this.code = 1;
        this.msg = msg;
    }

    /**
     * 通用的错误信息
     * @param code
     * @param msg
     */
    public ServiceException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 重写 getMessage 方法，返回自定义的错误消息
     * @return 错误消息
     */
    @Override
    public String getMessage() {
        return this.msg;
    }

}
