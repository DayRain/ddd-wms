package com.dayrain.wms.common.exception;

public class ServerException extends RuntimeException{

    private final ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ServerException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorCode.setErrorMessage(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
