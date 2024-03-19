package com.dayrain.wms.common.exception;

import com.dayrain.wms.common.exception.ErrorCode;

public enum DomainErrorCode implements ErrorCode {

    STOCK_ERROR(5000, "库存异常")
    ;
    private final int code;

    private String message;

    DomainErrorCode(int code, String message) {
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
