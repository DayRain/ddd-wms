package com.dayrain.wms.common.exception;


public class DomainException extends ServerException {

    public DomainException(DomainErrorCode errorCode) {
        super(errorCode);
    }

    public DomainException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
