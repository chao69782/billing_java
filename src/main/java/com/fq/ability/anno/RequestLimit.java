package com.fq.ability.anno;

import java.lang.annotation.*;

/**
 * @Author 超chao
 * @Description 防止用户重复提交注解
 * @Date 2025/11/11 周二 12:49
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
    //默认请求间隔1秒
    long time() default 1;
    //默认提示语
    String message() default "手速太快了";
}
