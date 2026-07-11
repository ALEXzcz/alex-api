package com.ctgu.alexapi.utils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class ApiResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -8440958610795020343L;

    // 状态码常量
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;
    public static final int CODE_WARNING = 501;
    public static final int CODE_NOT_PERMISSION = 403;
    public static final int CODE_NOT_LOGIN = 401;
    public static final int CODE_INVALID_REQUEST = 400;

    private int code;
    private String msg;
    private Object data;

    public ApiResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功返回
    public static ApiResult success() {
        return new ApiResult(CODE_SUCCESS, null, null);
    }

    public static ApiResult success(String msg) {
        return new ApiResult(CODE_SUCCESS, msg, null);
    }

    public static ApiResult success(String msg, Object data) {
        return new ApiResult(CODE_SUCCESS, msg, data);
    }

    public static ApiResult success(Object data) {
        return new ApiResult(CODE_SUCCESS, null, data);
    }

    // 错误返回
    public static ApiResult error() {
        return new ApiResult(CODE_ERROR, "出错了", null);
    }

    public static ApiResult error(String msg) {
        return new ApiResult(CODE_ERROR, msg, null);
    }

    public static ApiResult error(String msg, Object data) {
        return new ApiResult(CODE_ERROR, msg, data);
    }

    // 警告返回
    public static ApiResult warning(String msg) {
        return new ApiResult(CODE_WARNING, msg, null);
    }

    public static ApiResult warning(String msg, Object data) {
        return new ApiResult(CODE_WARNING, msg, data);
    }

    // 未登录
    public static ApiResult notLogin() {
        return new ApiResult(CODE_NOT_LOGIN, "未登录，请先进行登录", null);
    }

    // 权限不足
    public static ApiResult noPermissions() {
        return new ApiResult(CODE_NOT_PERMISSION, "权限不足", null);
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}