package com.deqiying.framework.enums;

import com.deqiying.framework.interfaces.StatusCode;
import lombok.Getter;


/**
 * 统一的响应状态码
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 18:04
 */
@Getter
public enum ResultCode implements StatusCode {
    SUCCESS("00000", "请求成功"),
    // 4000系列接口错误,
    BAD_REQUEST("A4000", "客户端请求的语法错误，服务器无法理解"),
    VALIDATE_ERROR("A4010", "参数校验失败"),
    REQUIRED_ERROR("A4020", "缺少必要参数"),
    NOT_FOUND("A4004","未找到请求的资源"),
    METHOD_NOT_ALLOWED("A4005","请求方式错误，请检查后重试。"),

    // 5000系列服务器错误,
    FAILED("B5000", "请求失败，服务器内部错误"),
    BUSINESS_ERROR("B5010", "业务处理出现异常"),
    SERVICE_ERROR("B5555", "服务器内部出现异常"),
    API_ERROR("B5020", "接口响应异常"),
    ERROR("B5050", "未知错误"),
    //2000系列用户错误
    USER_NOT_EXIST("A2000","用户不存在"),
    USER_LOGIN_FAIL("A2001","用户名或密码错误"),
    USER_NOT_LOGIN("A2002","用户还未登录,请先登录"),
    NO_PERMISSION("A2003","权限不足,请联系管理员"),


    // 7000系列response返回值封装失败
    RESPONSE_PACK_ERROR("B7000", "response返回值封装失败");


    private final String code;
    private final String msg;


    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
