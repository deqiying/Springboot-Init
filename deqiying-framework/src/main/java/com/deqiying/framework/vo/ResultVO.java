package com.deqiying.framework.vo;

import com.deqiying.framework.enums.ResultCode;
import com.deqiying.framework.interfaces.StatusCode;
import lombok.Data;

/**
 * 统一的响应体
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 17:52
 */
@Data
public class ResultVO<T> {
    /**
     * 状态码
     */
    private String code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 返回对象
     */
    private T data;

    /**
     * 服务响应时间戳
     */
    private Long timestamp;

    public ResultVO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ResultVO() {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(StatusCode statusCode, T data) {
        this(statusCode.getCode(), statusCode.getMsg(), data);
    }

    // 只返回状态码
    public ResultVO(StatusCode statusCode) {
        this(statusCode, null);
    }

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(data);
    }

    public static <T> ResultVO<T> error(StatusCode statusCode) {
        return new ResultVO<>(statusCode);
    }

    public static <T> ResultVO<T> failed(T data) {
        return new ResultVO<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), data);
    }

    public static <T> ResultVO<T> build(StatusCode statusCode, T data) {
        return new ResultVO<>(statusCode, data);
    }
}
