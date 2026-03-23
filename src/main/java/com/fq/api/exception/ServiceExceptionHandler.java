package com.fq.api.exception;

import com.fq.api.api.ApiError;
import com.fq.api.api.ApiRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


/**
 * @Author 超chao
 * @Description 全局异常处理器，用于统一处理系统中的各类异常
 * @Date 2024/10/12/周六 14:02
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler {

    /**
     * 捕获 ServiceException
     * @param e
     * @return
     */
    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiRest<Void> serviceExceptionHandler(ServiceException e) {
        return new ApiRest<>(e);
    }

    /**
     * 捕获参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiRest<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        ApiRest<Void> rest = new ApiRest<>();
        rest.setCode(1);
        rest.setMsg(message);
        return rest;
    }

    /**
     * 捕获参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ApiRest<Void> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        ApiRest<Void> rest = new ApiRest<>();
        rest.setCode(1);
        rest.setMsg(message);
        return rest;
    }

    /**
     * 捕获权限不足异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiRest<Void> accessDeniedExceptionHandler(AccessDeniedException e) {
        ApiRest<Void> rest = new ApiRest<>();
        rest.setCode(ApiError.ERROR_10030000.getCode());
        rest.setMsg(ApiError.ERROR_10030000.msg);
        return rest;
    }
}