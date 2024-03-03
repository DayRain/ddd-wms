package com.dayrain.wms.common.exception;
/**
 * 基础异常码
 * @author peng
 * @date 2024/3/3
 */
public enum CommonErrorCode implements ErrorCode{
    HTTP_PARAM_ERROR(4001, "HTTP参数校验异常")
    ;

    private final int code;
    private String message;

    CommonErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public void setErrorMessage(String message) {
        this.message = message;
    }
}
