package com.fq.ability.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.fq.ability.anno.Log;
import com.fq.api.exception.ServiceException;
import com.fq.modules.sys.log.entity.SysLog;
import com.fq.modules.sys.log.service.SysLogService;
import com.fq.utils.ServletUtils;
import com.fq.utils.user.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author 超chao
 * @Description 操作日志记录处理
 * @Date 2024/12/2/周一 17:12
 * @Version 1.0
 */
@Aspect
@Component
@Log4j2
public class LogAspect {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog) && @annotation(apiOperation)", returning = "jsonResult", argNames = "joinPoint,apiOperation,controllerLog,jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Operation apiOperation, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, apiOperation, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog) && @annotation(apiOperation)", throwing = "e", argNames = "joinPoint,apiOperation,controllerLog,e")
    public void doAfterThrowing(JoinPoint joinPoint, Operation apiOperation, Log controllerLog, Exception e) {
        handleLog(joinPoint, apiOperation, controllerLog, e, null);
    }

    protected void handleLog(JoinPoint joinPoint, Operation apiOperation, Log controllerLog, Exception e, Object jsonResult) {
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            SysLog sysLog = new SysLog();

            // 设置响应时间
            sysLog.setResponseTime(System.currentTimeMillis() - controllerLog.requestTime());

            // 设置请求url地址
            sysLog.setRequestUrl(Objects.requireNonNull(request).getRequestURI());

            // 异常请求设置
            this.setExceptionMessage(e, sysLog);

            // 设置注解上的参数
            this.setControllerMethodDescription(joinPoint, apiOperation, controllerLog, sysLog, jsonResult);

            // 设置用户 ID（登录接口可能没有用户信息，需要捕获异常）
            try {
                sysLog.setUserId(UserUtils.getUserId());
            } catch (Exception ex) {
                // 登录等接口可能没有用户信息，设置为 0
                sysLog.setUserId(0L);
            }

            if (controllerLog.isSaveSuccess()){
                if (sysLog.getStatus().equals(HttpStatus.SC_OK)){
                    sysLogService.saveLog(sysLog);
                }
            }else {
                // 保存数据库
                sysLogService.saveLog(sysLog);
            }


        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("记录系统日志异常，原因 {}", exp.getMessage());
        }
    }



    /**
     * 异常请求设置
     *
     * @param e          异常信息
     * @param sysOperLog 实体类
     */
    private void setExceptionMessage(Exception e, SysLog sysOperLog) {
        if (e != null) {
            // 设置异常信息
            if (e instanceof ServiceException) {
                // 业务异常
                sysOperLog.setErrorMsg(((ServiceException) e).getMsg());
                sysOperLog.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            } else {
                // 未知异常
                sysOperLog.setErrorMsg(e.getMessage());
                sysOperLog.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
        }else {
            sysOperLog.setStatus(HttpStatus.SC_OK);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param apiOperation
     * @param log          日志
     * @param operLog      操作日志
     */
    public void setControllerMethodDescription(JoinPoint joinPoint, Operation apiOperation, Log log, SysLog operLog, Object jsonResult) {
        // 获取 apiOperation 上的接口名
        String title = apiOperation.summary();
        if (StrUtil.isBlank(log.title())) {
            operLog.setTitle(title);
        }else {
            operLog.setTitle(log.title());
        }
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData()) {
            operLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, SysLog operLog) {
        String params = argsArrayToString(joinPoint.getArgs());
        operLog.setOperateParam(params);
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (ObjectUtil.isNotNull(o)) {
                    try {
                        String jsonObj = JSON.toJSONString(o);
                        params.append(jsonObj).append(",");
                    } catch (Exception ignored) {
                    }
                }

            }
        }
        String res = params.toString().trim();
        if (res.endsWith(",")) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

}
