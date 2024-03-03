package com.dayrain.wms.domain.exception;

import com.dayrain.wms.common.exception.ErrorCode;

public enum DomainErrorCode implements ErrorCode {
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
