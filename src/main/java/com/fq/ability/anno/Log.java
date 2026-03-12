package com.fq.ability.anno;

import java.lang.annotation.*;

/**
 * @Author 超chao
 * @Description 系统日志
 * @Date 2024/12/2/周一 16:11
 * @Version 1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 动作名称
     */
    String title() default "";

    /**
     * 请求时间
     */
    long requestTime() default 0L;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

    /**
     * 是否执行成功才保存
     */
    boolean isSaveSuccess() default true;
}
