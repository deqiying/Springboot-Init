package com.deqiying.framework.exception;

import com.deqiying.framework.enums.ResultCode;
import com.deqiying.framework.interfaces.StatusCode;
import lombok.Getter;

/**
 * 自定义服务内部异常
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 22:13
 */
@Getter
public class ServiceException extends RuntimeException implements StatusCode {
    /**
     * 状态码
     */
    private String code;
    /**
     * 状态消息
     */
    private String msg;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(StatusCode statusCode, String message) {
        // message用于用户设置抛出错误详情
        super(message);
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    /**
     * 默认接口处理异常
     *
     * @param message 异常消息
     */
    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.SERVICE_ERROR.getCode();
        this.msg = ResultCode.SERVICE_ERROR.getMsg();
    }

}
