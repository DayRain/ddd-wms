package com.dayrain.wms.domain.exception;


import com.dayrain.wms.common.exception.ErrorCode;
import com.dayrain.wms.common.exception.ServerException;

public class DomainException extends ServerException {

    public DomainException(DomainErrorCode errorCode) {
        super(errorCode);
    }

    public DomainException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
