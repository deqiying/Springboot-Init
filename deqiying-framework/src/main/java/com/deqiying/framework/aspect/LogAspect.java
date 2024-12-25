package com.deqiying.framework.aspect;

import com.deqiying.common.utils.spring.SpringUtils;
import com.deqiying.framework.annotation.LogRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口日志切面
 *
 * @author deqiying
 * @since 2024/12/26
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 环绕通知: 拦截带有@LogRequest注解的方法
     *
     * @param joinPoint  ProceedingJoinPoint
     * @param logRequest LogRequest
     * @return 切片执行结果
     */
    @SneakyThrows
    @Around("@annotation(logRequest)")
    public Object doAround(ProceedingJoinPoint joinPoint, LogRequest logRequest) {

        // 开始执行时间
        long startTime = System.currentTimeMillis();
        // 执行结果
        Object result = null;
        try {
            // 继续执行原方法
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            result = e;
            throw e;
        } finally {
            doLog(joinPoint, startTime, result);
        }
    }

    private void doLog(ProceedingJoinPoint joinPoint, long startTime, Object result) {
        try {
            HttpServletRequest request = SpringUtils.currentRequest();
            Map<String, Object> nameToValueMap = new HashMap<>();

            // 1. 收集URL参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.forEach((key, values) -> {
                if (values.length == 1) {
                    nameToValueMap.put(key, values[0]);
                } else {
                    nameToValueMap.put(key, Arrays.asList(values));
                }
            });

            // 2. 收集Body参数
            Object[] args = joinPoint.getArgs();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Parameter[] parameters = signature.getMethod().getParameters();

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    nameToValueMap.put(parameter.getName(), args[i]);
                }
            }

            try {
                // 3. 处理x-www-form-urlencoded格式请求体
                if (nameToValueMap.isEmpty() && request.getContentType() != null &&
                        request.getContentType().contains("application/x-www-form-urlencoded")) {
                    String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
                    Arrays.stream(body.split("&")).forEach(param -> {
                        String[] kv = param.split("=");
                        if (kv.length == 2) {
                            nameToValueMap.put(kv[0], kv[1]);
                        }
                    });
                }
            } catch (Throwable e) {
                log.error("LogAspect doLog error", e);
            }

        } catch (Throwable e) {
            log.error("LogAspect doLog error", e);
        }

    }
}
