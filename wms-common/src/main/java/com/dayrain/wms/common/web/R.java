package com.dayrain.wms.common.web;

import com.dayrain.wms.common.exception.ErrorCode;
import lombok.Data;

@Data
public class R {

    private static final int SUCCESS_CODE = 200;

    private int code;

    private String message;

    private Object data;

    public R(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(ErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
    }

    public static R success() {
        return new R(SUCCESS_CODE, "success");
    }

    public static R success(Object data) {
        return new R(SUCCESS_CODE, "success", data);
    }

    public static R error(ErrorCode errorCode) {
        return new R(errorCode);
    }

    public static R error(ErrorCode errorCode, String detailMessage) {
        return new R(errorCode.getErrorCode(), detailMessage);
    }
}
