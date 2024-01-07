package com.deqiying.framework.advice;


import com.deqiying.framework.enums.ResultCode;
import com.deqiying.framework.exception.ServiceException;
import com.deqiying.framework.vo.ResultVO;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一的接口异常拦截
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 17:53
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 处理404异常
     * 指定http状态码为404
     *
     * @param e NoHandlerFoundException
     * @return ResultVO
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultVO<String> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        return ResultVO.error(ResultCode.NOT_FOUND);
    }

    /**
     * 处理请求方式错误(405)异常
     * 指定http状态码为405
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ResultVO
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResultVO<String> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return ResultVO.error(ResultCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 处理参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return ResultVO
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<List<String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> msgList = null;
        // 从异常对象中拿到 FieldError 对象
        if (!e.getBindingResult().getFieldErrors().isEmpty()) {
            msgList = new ArrayList<>(e.getBindingResult().getFieldErrors().size());
            String msg = "%s: %s";
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                msgList.add(String.format(msg, fieldError.getField(), fieldError.getDefaultMessage()));
            }
        }
        return ResultVO.build(ResultCode.VALIDATE_ERROR, msgList);
    }

    /**
     * 处理参数校验异常
     *
     * @param e BindException
     * @return ResultVO
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<String> BindExceptionHandler(BindException e) {
        return ResultVO.build(ResultCode.VALIDATE_ERROR, e.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e ValidationException
     * @return ResultVO
     */
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<String> ConstraintViolationExceptionHandler(ValidationException e) {
        return ResultVO.build(ResultCode.VALIDATE_ERROR, e.getMessage());
    }

    /**
     * 处理自定义异常-APIException
     *
     * @param e APIException
     * @return ResultVO
     */
    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<String> APIExceptionHandler(ServiceException e) {
        return ResultVO.error(e);
    }

    /**
     * 处理空指针的异常
     *
     * @param e NullPointerException
     * @return
     * @description 空指针异常定义为前端传参错误，返回400
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResultVO<String> nullPointerException(NullPointerException e) {
        return ResultVO.error(ResultCode.SERVICE_ERROR);
    }

    /**
     * 处理其他异常,统一接口响应格式
     *
     * @param e Exception
     * @return ResultVO
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<String> AllExceptionHandler(Exception e) {
        return ResultVO.error(ResultCode.ERROR);
    }
}
