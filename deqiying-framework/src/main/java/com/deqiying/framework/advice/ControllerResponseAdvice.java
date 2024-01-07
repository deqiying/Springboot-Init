package com.deqiying.framework.advice;

import com.deqiying.framework.annotation.NotControllerResponseAdvice;
import com.deqiying.framework.enums.ResultCode;
import com.deqiying.framework.exception.ServiceException;
import com.deqiying.framework.vo.ResultVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一的接口响应体封装
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 18:03
 */
@RestControllerAdvice
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * response是ResultVo类型，或者注释了NotControllerResponseAdvice都不进行包装
     *
     * @return 是否进行增强？true：增强，false：不执行增强
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> selectedConverterType) {
        return !(methodParameter.getParameterType().isAssignableFrom(ResultVO.class)
                || methodParameter.hasMethodAnnotation(NotControllerResponseAdvice.class)
                || methodParameter.getContainingClass().isAnnotationPresent(NotControllerResponseAdvice.class));
    }

    /**
     * 仅当 supports 方法结果为true时执行
     *
     * @param body controller 返回的结果
     * @return 响应body
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
            try {
                // 将数据包装在ResultVo里后转换为json串进行返回
                return objectMapper.writeValueAsString(ResultVO.success(body));
            } catch (JsonProcessingException e) {
                throw new ServiceException(ResultCode.RESPONSE_PACK_ERROR, e.getMessage());
            }
        }
        // 否则直接包装成ResultVo返回
        return ResultVO.success(body);
    }
}