package com.dayrain.wms.common.exception;

public interface ErrorCode {

    int getErrorCode();

   String getErrorMessage();

   void setErrorMessage(String message);
}
